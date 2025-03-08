package com.example.security.service.impl;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.security.config.JWTService;
import com.example.security.domain.Emp;
import com.example.security.domain.LoginRequest;
import com.example.security.domain.LoginResponse;
import com.example.security.domain.ProfilePhoto;
import com.example.security.proxy.EmpProxy;
import com.example.security.repository.EmpRepo;
import com.example.security.repository.ProfilePhotoRepo;
import com.example.security.service.EmpService;
import com.example.security.utils.MapperUtils;

@Service
public class EmpServiceImpl implements EmpService
{

	@Autowired
	private EmpRepo empRepo;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private JWTService jwtService;
	
	@Autowired
	private AuthenticationManager authmanager;
	
	@Autowired
	private MapperUtils utils;
	
	@Autowired
	private ProfilePhotoRepo profilePhotoRepo;
	
	//save user
	@Override
	public String save(EmpProxy emp)
	{
	emp.setPassword(passwordEncoder.encode(emp.getPassword()));
	Emp convertEmpProxyToEntity = utils.convertEmpProxyToEntity(emp);
	empRepo.save(convertEmpProxyToEntity);
	return "saved";
	}
	
	
	//generate jwt tocken
	@Override
	public String generateTocken(Emp emp)
	{
		Emp findByUserName = empRepo.findByUserName(emp.getUserName());

		System.out.println(findByUserName);
		System.out.println(findByUserName.getPassword());
		//System.out.println(emp.getPassword());
		boolean matches = passwordEncoder.matches(emp.getPassword(),findByUserName.getPassword());
		
		System.out.println("Matches Password:"+matches);
		if(!matches)
		{
		return "user not found";
		}
		return jwtService.genearteTocken(emp.getUserName());
	}
	
	//login
	@Override
	public LoginResponse login(LoginRequest loginRequest)
	{
		//System.out.println("login response from emp service called..");
		Authentication authentication=new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword());
		
		Authentication verified = authmanager.authenticate(authentication);
		
		//verified.getAuthorities().stream().forEach(a->System.out.println("Authorities:"+a));
		if(!verified.isAuthenticated())
		{
			//System.out.println("user not found");
			//System.err.println("user not found");
			//throw new BadCredicialException(null, null);
			//throw new BadCredentialsException("Bad credentials...");
			System.out.println("bad credials..");
			//throw new ErrorResponse("bad credentials",404);
		}
		
		 return new LoginResponse(loginRequest.getUserName(),jwtService.genearteTocken(loginRequest.getUserName()),(List<SimpleGrantedAuthority>) verified.getAuthorities());	 
	}
	
	//get all employee
	@Override
	public List<EmpProxy> getAllEmp()
	{
		List<Emp> findAll = empRepo.findAll();
		return utils.convertListOfEmpToproxy(findAll);
	}
	
	
	//upload image to dynamic path
	@Override
	public String uploadImgToDynamicPath(MultipartFile file,EmpProxy empProxy) {
		String fileName=null;
		
		ProfilePhoto profileImg=null;
		
		try {
			
			String urlPath = new ClassPathResource("").getFile().getAbsolutePath() + File.separator + "static"+ File.separator + "documents";
		
			//String urlPath="C:\\Users\\RiyaRami\\Documents\\workspace-spring-tool-suite-4-4.17.2.RELEASE\\uploadImgFile\\src\\main\\resources\\static\\documents";
		
			File f=new File(urlPath);
			
			if(!f.exists())
			{
				f.mkdir();
			}
			
		  fileName=file.getOriginalFilename();
			
		String finalPath=urlPath+File.separator+fileName;
		
		//copy file path into dynamic path
		Files.copy(file.getInputStream(),Paths.get(finalPath),StandardCopyOption.REPLACE_EXISTING);
	 
		ProfilePhoto pid=new ProfilePhoto();
		 pid.setFileName(finalPath);
		 pid.setContentType(file.getContentType());
		 pid.setFileSize(file.getSize());
		 pid.setFileId(utils.getUniqueFileName(finalPath));
		 pid.setImg(file.getBytes());

		//save image/file data in folder
		 profileImg=profilePhotoRepo.save(pid);
		 
		 //empProxy.setProfileImg(mapper.convertEntityToProxy(profileImg));
		 
		 Emp emp=utils.convertEmpProxyToEntity(empProxy);
		
		 //set emp id 
		 //uncomment- emp.setProfileImg(pid);
		 emp.setPassword(passwordEncoder.encode(emp.getPassword()));
		 emp.setOtp(generateOTP());
		 empRepo.save(emp);
		 
		 System.out.println(""+emp);
		 System.out.println(pid.getId());
		
			//empProxy.getProfileImg().setId(pid.getId());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return "File have been saved with "+(Objects.isNull(profileImg) ? "Id not found":profileImg.getId());
	}


	//delete by id
	@Override
	public String deleteById(Integer id) {
		empRepo.deleteById(id);
		return "deleted successfully";
	}
	
	//generateOTP
	@Override
	public String generateOTP()
	{
		Integer min=5000;
		Integer max=900000;
		Random random=new Random();
		//System.out.println(max + 1);
		//System.out.println(min);
		
		Integer nextInt = ThreadLocalRandom.current().nextInt(min, max + 1);
		String otp=nextInt.toString();
		
		//String substring = str.substring(0);
		//System.out.println(substring+"length:"+substring.length());
		//System.out.println(ThreadLocalRandom.current().nextInt(min, max + 1));
		//System.out.println(str.length());
		System.out.println("otp is..."+otp);
		return otp;
	}
	
	//verify otp
	@Override
	public String verifyOTP(String otp)
	{
		if(otp.equals(generateOTP()))
		{
			return "otp matched";
		}
		return "wrong otp..please try again..";
	}
}
