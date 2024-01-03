create trigger optimize_act_hi_detail
	after insert or update on ACT_HI_VARINST for each row 
	execute function variable_copy_hi_detail_trigger();