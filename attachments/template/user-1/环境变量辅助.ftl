<#--如果表中文名存在则取中文名否则取表对应的实体类名-->
<#assign domainChineseDescription=((domainZnName?trim!"")?length > 0)?then(domainZnName, domainName)>

<#--约定utils目录基于service目录-->
<#assign utilsPackage=StrUtil.subBefore("${servicePackage}", ".", true) + "." + "utils">

