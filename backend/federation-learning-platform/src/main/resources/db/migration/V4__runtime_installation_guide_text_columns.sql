do $$
begin
    if exists (
        select 1
        from information_schema.tables
        where table_name = 'runtime_installation_guide'
    ) then
        alter table runtime_installation_guide
            alter column bootstrap_command type text,
            alter column node_label_command type text,
            alter column node_taint_command type text,
            alter column runtime_agent_node_selector_yaml type text,
            alter column runtime_agent_tolerations_yaml type text,
            alter column bootstrap_config_yaml type text;
    end if;
end $$;
