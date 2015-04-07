<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Charge</title>
<script type="text/javascript" src="js/script.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.9.1.custom.min.js" charset="utf-8"></script>
<script type="text/javascript" src="js/page-js/commonDate.js" charset="utf-8"></script>
<link href="css/main.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/jquery-ui.css" />
<link rel="stylesheet" type="text/css" href="css/jquery.datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="css/jquery-ui-1.9.1.custom.min.css" />
<script type="text/javascript">
function enableChallengeStatus(challengeDetails)
{
	var challengeId=challengeDetails.id;
	var challengeValue=challengeDetails.value;
	var challengeIdValue=document.getElementById(challengeId).checked; 
	$.ajax({
	     url : "changeActiveStatusAction.do?challengeValue=" + challengeValue+"&challengeIdValue="+challengeIdValue,
	     type : "GET",
	     contentType : "application/json; charset=utf-8",
	     success : function(call) {
	     },
	     error : function() {
	     }
	   })
}
</script>
</head>
<body>
  <div class="container clearfix">
    <div class="conact-form">

      <p>&nbsp;</p>
      <h3 style="color: red;">${message}</h3>
      <form:form method="POST" action="addChallengeAction.do" modelAttribute="AddChallenge" autocomplete="off">

        <table width="100%" border="0">
          <tr>

            <td><div class="form-control">
                <label>
                  <p>Title</p> <form:input path="title" id="title" class="inputControl" placeholder="title"
                    required="autofocus" maxlength="100" />
                    <form:hidden path="challengeId" /> </label>
              </div>
            </td>
            <td><div class="form-control">

                <label>
                  <p>Price</p> <form:input path="price" class="inputControl" placeholder="Price" required="autofocus"
                    maxlength="10" /> </label>
              </div>
            </td>
            
          </tr>
          <tr>
           <td><div class="form-control">

                <label>
                  <p>Description</p> <form:textarea path="discription" id="discription" class="inputControl"
                    placeholder="Discription" required="autofocus" maxlength="2000" /> </label>
              </div></td>
          
            <td><div class="form-control">
                <label>
                  <p>Duration</p> <form:input path="duration" class="inputControl" placeholder="Duration"
                    required="autofocus" maxlength="10" /> </label>
              </div></td>
              </tr>
          <tr>
            <td><div class="form-control">
                <label>
                  <p>Term and Condition</p> <form:textarea path="challengeCondition" class="inputControl" placeholder="challengeCondition"
                    required="autofocus" maxlength="1000" /> </label>
              </div></td>
         
         
            <td><div class="form-control">
                <label>
                  <p>Technology</p> <form:textarea path="technology" class="inputControl" placeholder="Technology"
                    maxlength="500" /> </label>
              </div></td>
          </tr>
           <tr>
          <td colspan="2"><input type="submit" value="Submit" class="btn lg-btn" />
          </td>
          </tr>
      </table>
        <c:set var="count" value="0" scope="page" />

        <display:table name="allAddChallengeList" pagesize="20" class="basic-table" uid="addChallengeList">
          <c:set var="count" value="${count+1}" scope="page" />
          <display:column title="S.NO" style="width:1%;">
     ${count}
    </display:column>
          <%-- <display:column property="challengeCondition" title="Challenge Name" /> --%>
          <display:column title="Terms & Condition">
         
           <a href="viewChallenge.do?challengeId=${addChallengeList.challengeId}">${addChallengeList.challengeCondition}</a>
          </display:column>
          <display:column property="price" title="Price" />
          <display:column title="Edit">
          <a href="ChallengeListEdit.do?challengeId=${addChallengeList.challengeId}"> Edit</a>
          </display:column>
          <display:column title="Delete">
           <a href="ChallengeListdelete.do?challengeId=${addChallengeList.challengeId}"
              onclick="return confirm('Please confirm if you want to delete this batch!');">Delete</a>
          </display:column>
          <display:column title="Is_Active">
           <c:if test="${addChallengeList.isActive==1}">
          <form:checkbox path="isActive" checked="checked"  value="${addChallengeList.challengeId}" id="checkboxId${addChallengeList.challengeId}" onclick="enableChallengeStatus(this);"/>
          </c:if>
           <c:if test="${addChallengeList.isActive==0}">
          <form:checkbox path="isActive" value="${addChallengeList.challengeId}" id="checkboxId${addChallengeList.challengeId}" onclick="enableChallengeStatus(this);"/>
          </c:if>
          </display:column>
        </display:table>

      </form:form>
    </div>
  </div>
</body>
</html>