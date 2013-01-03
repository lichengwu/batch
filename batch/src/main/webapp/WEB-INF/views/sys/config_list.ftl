<html>
<head>
    <title>config list</title>
</head>
<body>

<table>
    <tr>
        <th>name space</th>
        <th>name</th>
        <th>value</th>
        <th>add time</th>
        <th>update time</th>
        <th>note</th>
    </tr>
<#list configs as config>
    <tr>
        <td>${config.namespace!''}</td>
        <td>${config.name!''}</td>
        <td><#if config.name?index_of('password')==-1 >
            ${config.value!''}
            <#else >
                ******
            </#if>
        </td>
        <td>${config.addTime?string('yyyy-MM-dd HH:mm:ss')}</td>
        <td><#if config.modTime??>${config.modTime?string('yyyy-MM-dd')}</#if></td>
        <td>${config.note!''}</td>
    </tr>

</#list>

</table>

</body>

</html>