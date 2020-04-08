package org.ht.controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JFileChooser;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.ht.pojo.Approveitem;
import org.ht.pojo.Employee;
import org.ht.pojo.Withdrawal;
import org.ht.service.ApproveService;
import org.ht.service.WithdrawalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("wd")
public class WithdrawalController {
	String str = "WEB-INF/view/";
	@Autowired
	private WithdrawalService ws;

	@RequestMapping("wlist")
	public String withdrawallist(
			Model m,
			@RequestParam(value = "currpage", required = false) String currpage,
			@RequestParam(value = "btn", required = false) String btn,
			HttpServletRequest req,
			@RequestParam(value = "wname", required = false) String wname,
			@RequestParam(value = "yyy", required = false) String yyy,
			@RequestParam(value = "yyyy", required = false) String yyyy,
			@RequestParam(value = "wstatu", required = false) String wstatu)
			throws UnsupportedEncodingException {
		HttpSession session = req.getSession();
		session.setAttribute("btn", btn);
		
		Map<String, Object> findmap = new HashMap<String, Object>();
		findmap.put("wname", wname);
		findmap.put("yyy", yyy);
		findmap.put("yyyy", yyyy);
		findmap.put("wstatu", wstatu);
		req.setCharacterEncoding("utf-8");
		
		session.setAttribute("wname", wname);
		session.setAttribute("yyy", yyy);
		session.setAttribute("yyyy", yyyy);
		session.setAttribute("wstatu", wstatu);

		Map<String, Object> wmap = ws.withdrawallist(currpage, btn, findmap);
		List<Withdrawal> llist = (List<Withdrawal>) wmap.get("llist");
		m.addAttribute("pagerow", wmap.get("pagerow"));
		m.addAttribute("currpages", wmap.get("currpages"));
		m.addAttribute("wdlist", llist);
		m.addAttribute("totalpage", wmap.get("totalpage"));
		m.addAttribute("totalrow", wmap.get("totalrow"));
		int suntxmoney =  ws.sumtxmoney();
		m.addAttribute("suntxmoney", suntxmoney);
		int sumdzmoney =  ws.sumdzmoney();
		m.addAttribute("sumdzmoney", sumdzmoney);
		int sumsxf =  ws.sumsxf();
		m.addAttribute("sumsxf", sumsxf);
		return str + "Withdrawallist";
	}
	
	//ajax
	@RequestMapping("ajax")
	@ResponseBody
	public Withdrawal ajax(@RequestParam(value = "id", required = false) int id){
		System.out.println(id);
		System.out.println(ws.selectone(id).getUname());
		return ws.selectone(id);
	}
	
	//Successful or failed transfer
	@RequestMapping("zhuans")
	public String zhuan(@RequestParam(value = "gg", required = false) int gg,
			@RequestParam(value = "wid", required = false) int wid){
		Withdrawal wone =  ws.selectone(wid);
		if(gg==0){
			//fail
			ws.updwith(0, wid);
			//refund
			
			Integer txmoney = Integer.parseInt(wone.getTxmoney());//Medical examination amount
			Integer uid = wone.getuID();//user id
			ws.updmoney(txmoney, uid);
			int i=1;
			//Add failed transaction records
			ws.intmoney(wone, i);
		}else if(gg==1){
			//success
			ws.updwith(gg, wid);
			int i=2;
			//Add transaction success record
			ws.intmoney(wone, i);
		}
		return "redirect:wlist.do";
	}
	//examination passed
		@RequestMapping("shen")
		public String shen(@RequestParam(value = "gg", required = false) int gg,
				@RequestParam(value = "wid", required = false) int wid,HttpServletRequest req){
			HttpSession session = req.getSession();
			Employee emp = (Employee) session.getAttribute("globalemp");
			String shname = emp.getEname();
			if(gg==0){
				//Failure, Need to change to failure and modify the transfer time, reviewer time, reviewer
				ws.updwiths(gg, wid, shname); 
				//refund
				Withdrawal wone =  ws.selectone(wid);
				Integer txmoney = Integer.parseInt(wone.getTxmoney());//体检金额
				Integer uid = wone.getuID();//用户id
				ws.updmoney(txmoney, uid);
				int i=0;
				//Add failed transaction records
				ws.intmoney(wone, i);
			}else if(gg==2){
				//Success needs to be changed to transfer and the transfer time, reviewer time, reviewer
				
				ws.updwiths(gg, wid, shname);
				
			}
			return "redirect:wlist.do";
		}
	/**
	 * export excel
	 * 
	 * @throws IOException
	 */
	@RequestMapping("putexcel")
	public String putexcel(HttpServletResponse response) throws IOException {
		HSSFWorkbook workBook = new HSSFWorkbook();
		HSSFSheet sheet = workBook.createSheet("Withdrawal management");
		HSSFRow titleRow = sheet.createRow(0);
		// title line
		HSSFCell cell1 = titleRow.createCell(0);
		cell1.setCellValue("UserID");
		HSSFCell cell2 = titleRow.createCell(1);
		cell2.setCellValue("USerName");
		HSSFCell cell3 = titleRow.createCell(2);
		cell3.setCellValue("RealName");
		HSSFCell cell4 = titleRow.createCell(3);
		cell4.setCellValue("Account");
		HSSFCell cell5 = titleRow.createCell(4);
		cell5.setCellValue("WithdrawBank");
		HSSFCell cell6 = titleRow.createCell(5);
		cell6.setCellValue("WithdrawAmount");
		HSSFCell cell7 = titleRow.createCell(6);
		cell7.setCellValue("Amount received");
		HSSFCell cell8 = titleRow.createCell(7);
		cell8.setCellValue("Handling fee");
		HSSFCell cell9 = titleRow.createCell(8);
		cell9.setCellValue("Withdrawal time");
		HSSFCell cell10 = titleRow.createCell(9);
		cell10.setCellValue("Transfer Time");
		HSSFCell cell11 = titleRow.createCell(10);
		cell11.setCellValue("Status 0 failed, 1 has been withdrawn, 2 in transfer, 3 in review,)");
		HSSFCell cell12 = titleRow.createCell(11);
		cell12.setCellValue("Reviewer");
		HSSFCell cell13 = titleRow.createCell(12);
		cell13.setCellValue("Remarks");

		List<Withdrawal> lw = ws.selectallw();
		for (int i = 0; i < lw.size(); i++) {
			Withdrawal wi = lw.get(i);
			// data row
			HSSFRow dataRow = sheet.createRow(i + 1);
			HSSFCell uid = dataRow.createCell(0);
			uid.setCellValue(wi.getuID());
			HSSFCell uname = dataRow.createCell(1);
			uname.setCellValue(wi.getUname());
			HSSFCell zname = dataRow.createCell(2);
			zname.setCellValue(wi.getZname());
			HSSFCell txnum = dataRow.createCell(3);
			txnum.setCellValue(wi.getTxnum());
			HSSFCell txbank = dataRow.createCell(4);
			txbank.setCellValue(wi.getTxbank());
			HSSFCell txmoney = dataRow.createCell(5);
			txmoney.setCellValue(wi.getTxmoney());
			HSSFCell dzmoney = dataRow.createCell(6);
			dzmoney.setCellValue(wi.getDzmoney());
			HSSFCell sxf = dataRow.createCell(7);
			sxf.setCellValue(wi.getSxf());
			HSSFCell txtime = dataRow.createCell(8);

			HSSFCellStyle dateStyle = workBook.createCellStyle();
			HSSFDataFormat dateFormat = workBook.createDataFormat();
			dateStyle
					.setDataFormat(dateFormat.getFormat("yyyy-MM-dd HH:mm:ss"));
			txtime.setCellStyle(dateStyle);

			txtime.setCellValue(wi.getTxtime());
			HSSFCell zztime = dataRow.createCell(9);

			zztime.setCellStyle(dateStyle);

			zztime.setCellValue(wi.getZztime());
			HSSFCell statu = dataRow.createCell(10);
			statu.setCellValue(wi.getStatu());
			HSSFCell shwho = dataRow.createCell(11);
			shwho.setCellValue(wi.getShwho());
			HSSFCell nothing = dataRow.createCell(12);
			nothing.setCellValue(wi.getNothing());
		}
		
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.showOpenDialog(null);
		String path = chooser.getSelectedFile().getPath();	
		
		if(path!=null&&!path.equals("")){
		
			FileOutputStream fos = new FileOutputStream(
					path+"\\Withdraw Info.xls");
			workBook.write(fos);
		}
		
		return "redirect:wlist.do";
	}
}
