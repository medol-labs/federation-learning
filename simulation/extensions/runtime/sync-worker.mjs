import {readFileSync} from 'node:fs';
import {spawnSync} from 'node:child_process';
import {executionCommand} from '../../src/pipeline.mjs';

const config = JSON.parse(readFileSync(process.argv[2], 'utf8'));
const host = config.hosts?.k3d;
executionCommand({command: ['true']}, host, 'validate');
if (host?.transport !== 'ssh') throw new Error('Expected an SSH k3d host');
const quote = value => "'" + value.replaceAll("'", "'\\''") + "'";
function run(command, args) {
    const result = spawnSync(command, args, {stdio: 'inherit', timeout: 600000});
    if (result.error || result.status !== 0) throw new Error(`${command} failed: ${result.error?.message || result.status}`);
}
run('ssh', ['-o', 'BatchMode=yes', '-o', 'ConnectTimeout=15', host.target, `mkdir -p ${quote(host.workDir)}`]);
// Whitelisted worker inputs only. Preserve remote machine configuration and secrets.
const version = spawnSync('rsync', ['--version'], {encoding: 'utf8'});
const major = Number(version.stdout?.match(/version\s+(\d+)/)?.[1]);
if (!major) throw new Error('Cannot determine rsync version');
if (major < 3 && !/^\/[a-zA-Z0-9_./-]+$/.test(host.workDir)) {
    throw new Error('Legacy rsync requires a remote path without spaces or shell metacharacters');
}
run('rsync', ['-azR', ...(major >= 3 ? ['--protect-args'] : []), '-e', 'ssh -o BatchMode=yes -o ConnectTimeout=15',
    '--exclude=.env', '--exclude=*.private.*', '--exclude=secrets.*.yaml', '--exclude=.work/',
    '--exclude=reports/', '--exclude=node_modules/', '--exclude=.git/', '--exclude=*.tar', '--exclude=*.tar.gz',
    '--exclude=registries.yaml',
    'operations/', 'simulation/', 'scripts/', 'dictionary-init/',
    `${host.target}:${host.workDir}/`]);
