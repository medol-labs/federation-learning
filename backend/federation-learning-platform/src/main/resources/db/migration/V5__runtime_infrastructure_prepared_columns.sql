do $$
begin
    if exists (
        select 1
        from information_schema.tables
        where table_name = 'runtime_installation_plan_catalog'
    ) then
        alter table runtime_installation_plan_catalog
            add column if not exists prepared_at timestamp,
            add column if not exists prepared_node_count integer;
    end if;

    if exists (
        select 1
        from information_schema.tables
        where table_name = 'runtime_infrastructure_access_view'
    ) then
        alter table runtime_infrastructure_access_view
            add column if not exists infrastructure_prepared_at timestamp,
            add column if not exists prepared_node_count integer;
    end if;
end $$;
