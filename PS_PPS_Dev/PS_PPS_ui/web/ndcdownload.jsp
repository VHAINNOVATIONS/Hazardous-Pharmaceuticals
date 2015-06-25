<%@ page import="java.io.*,java.util.*" %> 
<%
try
{
	OutputStream output = response.getOutputStream();
	InputStream inStream = new FileInputStream("c:/testfile.txt");
	//response.reset();
	//response.setContentType("application/text");
	Calendar cal = new GregorianCalendar();
	int m = cal.get(GregorianCalendar.MONTH) + 1;
	int d = cal.get(GregorianCalendar.DATE);
	int h = cal.get(GregorianCalendar.HOUR);
	int min = cal.get(GregorianCalendar.MINUTE);
	String mm = Integer.toString(m);
	String dd = Integer.toString(d);
	String hh = Integer.toString(h);
	String minm = Integer.toString(min);
	String currentDate =  cal.get(GregorianCalendar.YEAR) + (m < 10 ? "0" + mm : mm) +
	     (d < 10 ? "0" + dd : dd) + (h < 10 ? "0" + hh : hh) + (min < 10 ? "0" + minm : minm);
	String prodfilename = "NDCCSV" + currentDate;
	response.setHeader("Content-disposition","attachment; filename=" + prodfilename);
	byte[] buf = new byte[1024];
	int len;
	while ((len = inStream.read(buf)) > 0){
		response.getOutputStream().write(buf, 0, len);
		}
	inStream.close();
	response.getOutputStream().flush(); 
}
catch(Exception e){
	e.printStackTrace();
}
%>