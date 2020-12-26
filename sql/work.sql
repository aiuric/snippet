# PMS 업데이트 후설정정보 확인 요청

select PMS.device_id, PMS.ems_ver, PMS.pcs_ver, CONFIG.basicmode_cd, CONFIG.backup_soc, CONFIG.hysteresis_low, CONFIG.hysteresis_high
from tb_bas_device_pms PMS, tb_opr_ess_config_mon_v2 CONFIG, tb_opr_ess_state STATE
where PMS.device_id = CONFIG.device_id and PMS.activation = 'Y' and PMS.device_id = STATE.device_id and STATE.is_connect = true order by device_id;
