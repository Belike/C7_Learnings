	INSERT INTO
		act_hi_detail(ID_,TYPE_,TIME_,NAME_,PROC_DEF_KEY_,PROC_DEF_ID_,ROOT_PROC_INST_ID_,PROC_INST_ID_,EXECUTION_ID_,CASE_DEF_KEY_,CASE_DEF_ID_,CASE_INST_ID_,	CASE_EXECUTION_ID_,TASK_ID_,ACT_INST_ID_,VAR_INST_ID_,VAR_TYPE_,REV_,BYTEARRAY_ID_,DOUBLE_,LONG_,TEXT_,TEXT2_,SEQUENCE_COUNTER_,TENANT_ID_,OPERATION_ID_,REMOVAL_TIME_,INITIAL_)
		SELECT ID_,'VariableUpdate',CURRENT_TIMESTAMP,NAME_,PROC_DEF_KEY_,PROC_DEF_ID_,ROOT_PROC_INST_ID_,PROC_INST_ID_,EXECUTION_ID_,CASE_DEF_KEY_,CASE_DEF_ID_,CASE_INST_ID_,CASE_EXECUTION_ID_,TASK_ID_,ACT_INST_ID_,ID_,VAR_TYPE_,REV_,BYTEARRAY_ID_,DOUBLE_,LONG_,TEXT_,TEXT2_,1,null,null,null,true
		from public.ACT_HI_VARINST
	on conflict (id_)
		do update set
			time_ = current_timestamp,
			name_ = excluded.name_,
			proc_def_key_ = excluded.proc_def_key_,
			ROOT_PROC_INST_ID_ = excluded.ROOT_PROC_INST_ID_,
			PROC_INST_ID_ = excluded.PROC_INST_ID_,
			EXECUTION_ID_ = excluded. EXECUTION_ID_,
			CASE_DEF_KEY_ = excluded.CASE_DEF_KEY_,
			CASE_DEF_ID_ = excluded.CASE_DEF_ID_,
			CASE_INST_ID_ = excluded.CASE_INST_ID_,
			CASE_EXECUTION_ID_ = excluded.CASE_EXECUTION_ID_,
			TASK_ID_ = excluded.TASK_ID_,
			ACT_INST_ID_ = excluded.ACT_INST_ID_,
			VAR_INST_ID_ = excluded.id_,
			VAR_TYPE_ = excluded.VAR_TYPE_,
			REV_ = excluded.REV_,
			BYTEARRAY_ID_ = excluded.BYTEARRAY_ID_,
			DOUBLE_ = excluded.DOUBLE_,
			long_ = excluded.long_,
			TEXT_ = excluded.TEXT_,
			TEXT2_ = excluded.TEXT2_;