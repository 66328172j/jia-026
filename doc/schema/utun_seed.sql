-- utun 舱室档案种子（F6 档案联动/停用引用验收依赖：id=0 在用、id=1 停用）
-- 阈值 1/5/10 与 spec_utun F6 calc band pairs=[(1,1),(5,2),(10,3),(20,4)] 同口径
INSERT INTO t_utun_chamber (id, site_no, site_name, site_type, road_name, th1_max, th2_max, th3_max, status, del_flag, create_by, create_time)
VALUES
 (0, 'CH00', '东线综合舱', '综合舱', '东线', 1.00, 5.00, 10.00, 0, 0, 'seed', NOW()),
 (1, 'CH01', '西线电力舱', '电力舱', '西线', 1.00, 5.00, 10.00, 1, 0, 'seed', NOW())
ON DUPLICATE KEY UPDATE site_name = VALUES(site_name), th1_max = VALUES(th1_max),
 th2_max = VALUES(th2_max), th3_max = VALUES(th3_max), status = VALUES(status), del_flag = 0;
