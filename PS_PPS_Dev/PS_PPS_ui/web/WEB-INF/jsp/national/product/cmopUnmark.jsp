<%@ taglib 
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core" %>  	
<%@ taglib
    prefix="peps"
    tagdir="/WEB-INF/tags"%>

<c:set
    scope="page"
    var="itemId"
    value="${item.id}" />

<center><h1>You are editing <peps:text key="product.long.name" label="false" item="${item}" /></h1></center>
<table width="713" height="400" border="1" cellspacing="2" cellpadding="2">
  <tr>
    <td width="701" valign="top" bgcolor="#FFFFFF"><table width="700" height="363">
      <tr>
        <td width="344" valign="middle">VA Print Name:</td>
        <td width="344" valign="middle">Order Unit: </td>
      </tr>
      <tr>
        <td valign="middle">VA Drug Class:</td>
        <td valign="middle">Product Dispense Units per Order Unit:</td>
      </tr>
      <tr>
        <td valign="middle">VA Dispense Unit:</td>
        <td valign="middle">Dispense Unit:</td>
      </tr>
      <tr>
        <td valign="middle">CMOP ID:</td>
        <td valign="middle">Price Per Dispense Unit:</td>
      </tr>
      <tr>
        <td colspan="2" valign="top"><p><br />
          UNMARK this drug to Transmit to CMOP? &nbsp;&nbsp;&nbsp;&nbsp;

            <select name="select" size="1" id="select">
              <option>Yes</option>
              <option>No</option>
            </select>
</p>
          <p align="left">
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit" name="button3" id="button3" value="Cancel" />
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <input type="submit" name="button" id="button" value="Apply Changes" />
          </p>
          <p>&nbsp;</p></td>
    </tr></table>    </td>
  </tr>
</table>
<p>&nbsp;</p>