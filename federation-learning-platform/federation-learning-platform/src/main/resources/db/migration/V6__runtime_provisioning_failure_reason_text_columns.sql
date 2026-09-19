do $$
declare
    item record;
begin
    for item in
        select *
        from (
            values
                ('runtime_infrastructure_access_view', 'infrastructure_verification_failure_reason'),
                ('runtime_infrastructure_access_view', 'agent_deployment_failure_reason'),
                ('runtime_infrastructure_access_view', 'agent_deployment_retry_failure_reason'),
                ('runtime_installation_plan_catalog', 'verification_failure_reason'),
                ('runtime_installation_plan_catalog', 'agent_deployment_failure_reason'),
                ('runtime_installation_plan_catalog', 'agent_deployment_retry_failure_reason')
        ) as columns_to_update(table_name, column_name)
    loop
        if exists (
            select 1
            from information_schema.columns
            where table_name = item.table_name
              and column_name = item.column_name
        ) then
            execute format(
                'alter table %I alter column %I type text',
                item.table_name,
                item.column_name
            );
        end if;
    end loop;
end $$;
