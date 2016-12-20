package {PACKAGE_NAME};
        import java.util.Date;
        import org.apache.ibatis.type.Alias;

/**
 *  @author JerryChien
 *  @copyright JerryChien
 */
@Alias("${tableName?uncap_first}")
public class ${tableName} {

<#list columns as column>
private ${column.javaColumnTypeName} ${column.javaColumnName?uncap_first};
</#list>

<#list columns as column>
public ${column.javaColumnTypeName} get${column.javaColumnName}() {
        return this.${column.javaColumnName?uncap_first}
        }

public void set${column.javaColumnName}(${column.javaColumnTypeName} ${column.javaColumnName?uncap_first}) {
        this.${column.javaColumnName?uncap_first} = ${column.javaColumnName?uncap_first}
        }
</#list>

        }