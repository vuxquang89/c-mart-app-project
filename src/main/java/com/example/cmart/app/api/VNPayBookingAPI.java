package com.example.cmart.app.api;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cmart.app.dto.PaymentInfoDTO;
import com.example.cmart.app.entity.BookingEntity;
import com.example.cmart.app.service.BookingService;
import com.example.cmart.app.service.VNPayService;
import com.example.cmart.app.util.PaymentMethod;

@RestController
@RequestMapping("/api")
public class VNPayBookingAPI {

	@Autowired
    private VNPayService vnPayService;
	@Autowired
	private BookingService bookingService;
	
	/**
	 * khi khách hàng chọn hình thức thanh toán qua vnpay
	 * @param id
	 * @return thong tin thanh toán
	 */
	@GetMapping("/customer/booking/paymentInfo/{id}")
	public ResponseEntity<?> getPaymentInfo(@PathVariable("id") long id) {
		BookingEntity bookingEntity = bookingService.findById(id).orElse(null);
		if(bookingEntity != null) {
			PaymentInfoDTO paymentInfo = new PaymentInfoDTO();
			paymentInfo.setId(id);
			paymentInfo.setAmount(bookingEntity.getTotalFare());
			paymentInfo.setContentPayment("Thanh toan chuyen di voi ma @" + id);
			return new ResponseEntity<>(paymentInfo, HttpStatus.OK);
		}else {
			Map<String, String> mess = new HashMap<String, String>();
			mess.put("warning", "Do not find booking id : " + id);
			return new ResponseEntity<>(mess, HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * khi khách hàng bấm đồng ý thanh toán
	 * @param paymentInfo
	 * @param request
	 * @return link thanh toán vnpay
	 */
	@PostMapping("/customer/booking/submitPayment")
	public ResponseEntity<?> submitPayment(@RequestBody PaymentInfoDTO paymentInfo, HttpServletRequest request) {
		String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String vnpayUrl = vnPayService.createBook(paymentInfo.getAmount(), paymentInfo.getContentPayment(), baseUrl);
        
        Map<String, String> mess = new HashMap<String, String>();
		mess.put("url_redirect", vnpayUrl);
		return new ResponseEntity<>(mess, HttpStatus.OK);
	}
	
	/**
	 * nhận kết quả trả về từ vnpay
	 * @param request
	 * @return
	 */
	@GetMapping("/customer/vnpay-payment")
	public ResponseEntity<?> getMapping(HttpServletRequest request){
        int paymentStatus = vnPayService.orderReturn(request);

        String orderInfo = request.getParameter("vnp_OrderInfo");
        
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = Integer.parseInt(request.getParameter("vnp_Amount"))/100 + "";

        Map<String, String> result = new HashMap<String, String>();
        result.put("orderId", orderInfo);
        result.put("totalPrice", totalPrice);
        result.put("paymentTime", paymentTime);
        result.put("transactionId", transactionId);
        if(paymentStatus == 1) {
        	long bookingId = getId(orderInfo);
        	BookingEntity booking = bookingService.findById(bookingId).orElse(null);
        	booking.setPaymentStatus(true);
        	booking.setPaymentMethod(PaymentMethod.banking);
        	bookingService.save(booking);
        	result.put("result", "paymentsuccess");
        }else {
        	result.put("result", "paymentfail");
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
	
	private long getId(String str) {
		String[] arrOfStr = str.split("@");
		return Long.parseLong(arrOfStr[1]);
	}
}
