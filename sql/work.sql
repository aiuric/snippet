# PMS 업데이트 후설정정보 확인 요청

select PMS.device_id, PMS.ems_ver, PMS.pcs_ver, CONFIG.basicmode_cd, CONFIG.backup_soc, CONFIG.hysteresis_low, CONFIG.hysteresis_high
from tb_bas_device_pms PMS, tb_opr_ess_config_mon_v2 CONFIG, tb_opr_ess_state STATE
where PMS.device_id = CONFIG.device_id and PMS.activation = 'Y' and PMS.device_id = STATE.device_id and STATE.is_connect = true order by device_id;


# stable update
sprintf(query, "WITH M2M AS ( SELECT IP FROM DC_CNF_MANAGER WHERE ID = 'M2M1' )"
               ", A AS ("
               "SELECT PRODUCT_MODEL_NM,"
               "       GRP_ID, "
               "       EMS_MODEL_NM, "
               "       PCS_MODEL_NM, "
               "       BMS_MODEL_NM, "
               "       EMS_VER, "
               "       PCS_VER, "
               "       BMS_VER, "
               "       M2M.IP AS EMS_FW_URL, "
               "       M2M.IP AS PCS_FW_URL, "
               "       M2M.IP AS BMS_FW_URL, "
               "       TO_CHAR(EMS_FW_SIZE) AS EMSFS, "
               "       TO_CHAR(PCS_FW_SIZE) AS PCSFS, "
               "       TO_CHAR(BMS_FW_SIZE) AS BMSFS "
               "FROM TB_PLF_FIRMWARE_INFO , M2M "
               "WHERE STABLE_VER_FLAG = 'Y') "
               "SELECT A.PRODUCT_MODEL_NM,"
               "       COALESCE(GM.device_id, 'ALL') AS DEVICE_ID, "
               "       CASE COALESCE(GM.FIRMWARE_EXCEPTION, 'NULL')"
               "              WHEN 'NULL' THEN 'Y'"
               "              WHEN '0' THEN 'Y'"
               "              ELSE 'N'"
               "              END AS IS_UPDATE,"
               "       A.EMS_MODEL_NM, "
               "       A.PCS_MODEL_NM, "
               "       A.BMS_MODEL_NM, "
               "       A.EMS_VER, "
               "       A.PCS_VER, "
               "       A.BMS_VER, "
               "       A.EMS_FW_URL, "
               "       A.PCS_FW_URL, "
               "       A.BMS_FW_URL, "
               "       A.EMSFS, "
               "       A.PCSFS, "
               "       A.BMSFS "
               "FROM A "
               "LEFT JOIN TB_BAS_UPT_GRP_MGT GM "
               "ON (A.GRP_ID = GM.GRP_ID);");
