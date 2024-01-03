/* Remarks: Tenant ID is currently not set and no REMOVAL_TIME_ in place */
CREATE OR REPLACE FUNCTION public.variable_copy_hi_detail_trigger()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
begin
	if TG_OP = 'UPDATE' then
		update ACT_HI_DETAIL
		set
			time_ = current_timestamp,
			name_ = new.name_,
			proc_def_key_ = new.proc_def_key_,
			ROOT_PROC_INST_ID_ = new.ROOT_PROC_INST_ID_,
			PROC_INST_ID_ = new.PROC_INST_ID_,
			EXECUTION_ID_ = new. EXECUTION_ID_,
			CASE_DEF_KEY_ = new.CASE_DEF_KEY_,
			CASE_DEF_ID_ = new.CASE_DEF_ID_,
			CASE_INST_ID_ = new.CASE_INST_ID_,
			CASE_EXECUTION_ID_ = new.CASE_EXECUTION_ID_,
			TASK_ID_ = new.TASK_ID_,
			ACT_INST_ID_ = new.ACT_INST_ID_,
			VAR_INST_ID_ = new.id_,
			VAR_TYPE_ = new.VAR_TYPE_,
			REV_ = new.REV_::int,
			BYTEARRAY_ID_ = new.BYTEARRAY_ID_,
			DOUBLE_ = new.DOUBLE_,
			long_ = new.long_,
			TEXT_ = new.TEXT_,
			TEXT2_ = new.TEXT2_
		where id_ = new.id_;
    elseif TG_OP = 'DELETE' then
        DELETE FROM ACT_HI_DETAIL where ID_ = old.ID_;
	else
		INSERT INTO act_hi_detail(ID_,TYPE_,TIME_,NAME_,PROC_DEF_KEY_,PROC_DEF_ID_,ROOT_PROC_INST_ID_,PROC_INST_ID_,EXECUTION_ID_,CASE_DEF_KEY_,CASE_DEF_ID_,CASE_INST_ID_,	CASE_EXECUTION_ID_,TASK_ID_,ACT_INST_ID_,VAR_INST_ID_,VAR_TYPE_,REV_,BYTEARRAY_ID_,DOUBLE_,LONG_,TEXT_,TEXT2_,SEQUENCE_COUNTER_,TENANT_ID_,OPERATION_ID_,REMOVAL_TIME_,INITIAL_)
		values(
		new.ID_,
		'VariableUpdate',
		CURRENT_TIMESTAMP,
		new.NAME_,
		new.PROC_DEF_KEY_,
		new.PROC_DEF_ID_,
		new.ROOT_PROC_INST_ID_,
		new.PROC_INST_ID_,
		new.EXECUTION_ID_,
		new.CASE_DEF_KEY_,
		new.CASE_DEF_ID_,
		new.CASE_INST_ID_,
		new.CASE_EXECUTION_ID_,
		new.TASK_ID_,
		new.ACT_INST_ID_,
		new.ID_,
		new.VAR_TYPE_,
		new.REV_::int,
		new.BYTEARRAY_ID_,
		new.DOUBLE_,
		new.LONG_,
		new.TEXT_,
		new.TEXT2_,
		1,
		new.TENANT_ID_,
		null,
		new.REMOVAL_TIME_,
		true);
	end if;
	return new;
END;
$function$
;
