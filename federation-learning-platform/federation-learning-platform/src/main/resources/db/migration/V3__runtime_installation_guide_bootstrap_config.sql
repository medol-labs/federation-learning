do $$
begin
    if exists (
        select 1
        from information_schema.tables
        where table_name = 'runtime_installation_guide'
    ) then
        alter table runtime_installation_guide
            alter column bootstrap_command type text,
            add column if not exists node_label_command text,
            add column if not exists node_taint_command text,
            add column if not exists runtime_agent_node_selector_yaml text,
            add column if not exists runtime_agent_tolerations_yaml text,
            add column if not exists bootstrap_config_yaml text;
    end if;
end $$;
