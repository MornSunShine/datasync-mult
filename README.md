# datasync-mult
Like the datasync but modify the configuration to meet the requirements of mult tables synchronization.

##Characteristic
要实现多表中指定字段同步到单表的需求，首先，多表之间应该有外键或者其他的规则联系，
这样构成的特定字段列才存在可供更新检索的列，例如，由课程表整理出学生表与学科表组成的数据源，
更新的特征字段应该是学生与学科，如果这两个字段的值在同步检索时存在重复，则执行Update，否则执行Insert