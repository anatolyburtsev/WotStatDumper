Dump statistic for all wotb players in many threads and save data into separate sqlite dbs. 
For merging use this:
db="WOTB"; for i in *Temp*; do sqlite3 wotb.db "attach \"$i\" as toMerge; BEGIN; INSERT INTO $db SELECT * FROM toMerge.${db}; COMMIT;"; done
