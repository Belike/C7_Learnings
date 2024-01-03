CREATE OR REPLACE TRIGGER optimize_act_hi_detail_trigger
AFTER INSERT OR UPDATE ON ACT_HI_VARINST
                           FOR EACH ROW WHEN (NEW.NAME_ in ('CobCounterFileName', 'CobFileName', 'EDHFilesControlOK', 'GLType', 'EDHFilesControlMessages', 'EdhFileIdList'
                           ,'AccrualReversalIdentifyRecordOK', 'ApiInEntryPoint', 'CobCounterFileId', 'ControlInfoEnrichmentCheckedMessages', 'ControlInfoEnrichmentCheckedOK', 'ControlInfoEnrichmentReceivedOK'
                           ,'ControlInfoLoadDataCheckedMessages', 'ControlInfoLoadDataCheckedOK', 'ControlInfoLoadDataReceivedOK','DataLoadOK','Dependant decision results','Dependant decisions input combinations'
                           ,'Dependant decisions inputs','DependantDecisionResultsAppliedOK','DependantInputCombinationsReceivedOK','GLType','FileChargeType','FileName','Independant decision results'
                           ,'Independant decisions input combinations','Independant decisions inputs','IndependantDecisionResultsAppliedOK','IndependantInputCombinationsReceivedOK'
                           ,'PostponedRevenueCalculatedRecordedOK','ProcessCalcrecordpostprevenue','ProcessIdentrecordaccrrever','SkipControlInfoEnrichment','ValidateBaseEnrichmentOK'
                           ,'AggrControlsOK','CobFilesPrerequisitesMessages','CobFilesPrerequisitesOK','ControlInfoPostAggrReceivedOK','ControlInfoPreAggrReceivedOK','PostAggrControlMessages'
                           ,'PostAggrControlOK','PostingAckReceivedOK','PostingDate','PreAggrControlMessages','PreAggrControlOK','dailyPostingCounterFileName','ErrorDetails'))
BEGIN
       IF INSERTING THEN
            INSERT INTO ACT_HI_DETAIL (ID_,TYPE_,TIME_,NAME_,PROC_DEF_KEY_,PROC_DEF_ID_,ROOT_PROC_INST_ID_,PROC_INST_ID_,EXECUTION_ID_,CASE_DEF_KEY_,CASE_DEF_ID_,CASE_INST_ID_,	CASE_EXECUTION_ID_,TASK_ID_,ACT_INST_ID_,VAR_INST_ID_,VAR_TYPE_,REV_,BYTEARRAY_ID_,DOUBLE_,LONG_,TEXT_,TEXT2_,SEQUENCE_COUNTER_,TENANT_ID_,OPERATION_ID_,REMOVAL_TIME_,INITIAL_)
		    values(
		        :new.ID_,
		        'VariableUpdate',
		        CURRENT_TIMESTAMP,
		        :new.NAME_,
		        :new.PROC_DEF_KEY_,
		        :new.PROC_DEF_ID_,
		        :new.ROOT_PROC_INST_ID_,
		        :new.PROC_INST_ID_,
		        :new.EXECUTION_ID_,
		        :new.CASE_DEF_KEY_,
		        :new.CASE_DEF_ID_,
		        :new.CASE_INST_ID_,
		        :new.CASE_EXECUTION_ID_,
		        :new.TASK_ID_,
		        :new.ACT_INST_ID_,
		        :new.ID_,
		        :new.VAR_TYPE_,
		        :new.REV_,
		        :new.BYTEARRAY_ID_,
		        :new.DOUBLE_,
		        :new.LONG_,
		        :new.TEXT_,
		        :new.TEXT2_,
		        :NEW.REV_,
		        :new.TENANT_ID_,
		        null,
		        :new.REMOVAL_TIME_,
		        1);
    ELSIF UPDATING THEN
update ACT_HI_DETAIL
set
    time_              = current_timestamp,
    name_              = :new.name_,
    proc_def_key_      = :new.proc_def_key_,
    ROOT_PROC_INST_ID_ = :new.ROOT_PROC_INST_ID_,
    PROC_INST_ID_      = :new.PROC_INST_ID_,
    EXECUTION_ID_      = :new.EXECUTION_ID_,
    CASE_DEF_KEY_      = :new.CASE_DEF_KEY_,
    CASE_DEF_ID_       = :new.CASE_DEF_ID_,
    CASE_INST_ID_      = :new.CASE_INST_ID_,
    CASE_EXECUTION_ID_ = :new.CASE_EXECUTION_ID_,
    TASK_ID_           = :new.TASK_ID_,
    ACT_INST_ID_       = :new.ACT_INST_ID_,
    VAR_INST_ID_       = :new.id_,
    VAR_TYPE_          = :new.VAR_TYPE_,
    REV_               = :new.REV_,
    BYTEARRAY_ID_ = :new.BYTEARRAY_ID_,
    DOUBLE_ = :new.DOUBLE_,
    long_ = :new.long_,
    TEXT_ = :new.TEXT_,
    TEXT2_ = :new.TEXT2_
where id_ = :new.id_;
END IF;
END;