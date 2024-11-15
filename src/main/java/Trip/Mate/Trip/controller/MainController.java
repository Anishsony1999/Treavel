package Trip.Mate.Trip.controller;

import Trip.Mate.Trip.dto.HotelDto;
import Trip.Mate.Trip.dto.PackageDto;
import Trip.Mate.Trip.model.User;
import Trip.Mate.Trip.service.HotelService;
import Trip.Mate.Trip.service.PackBookingService;
import Trip.Mate.Trip.service.PackService;
import Trip.Mate.Trip.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;


@Controller
@RequiredArgsConstructor
public class MainController {

    private final  UserService userService;
    private final HotelService hotelService;
    private final PackService packService;
    private final PackBookingService bookingService;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/login")
    public String UserLogin(@RequestParam("username") String email, @RequestParam("password") String pass, HttpServletResponse response){
        System.out.println(email + pass);
        User user = userService.verifyUser(email, pass,response);
        if(user != null) return "redirect:admin-home";
        return "redirect:register";
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("user",new User());
        return "register";
    }

    @PostMapping("/register")
    public String userRegister(@ModelAttribute User user){
        userService.userRegister(user);
        return "redirect:login";
    }

    @GetMapping("/addHotel")
    public String hotelPage(Model model){
        model.addAttribute("hotelDto",new HotelDto());
        return "add-hotel";
    }

    @PostMapping("/addHotel")
    public String addHotel(@ModelAttribute HotelDto hotelDto, @RequestParam("image")MultipartFile image) throws IOException {
        System.out.println(image);
        hotelService.addHotel(hotelDto);
        return "redirect:addpackage";
    }

    @GetMapping("/addpackage")
    public String pack(Model model){
        model.addAttribute("pack",new PackageDto());
        return "add-package";
    }

    @PostMapping("/addpackage")
    public String addPackage(@ModelAttribute PackageDto packageDto,@RequestParam("image") MultipartFile image) throws IOException {
        packService.addPackImage(packageDto);
        return "redirect:admin-home";
    }

    @GetMapping("/admin-home")
    public String adminPage(){
        return "admin-home";
    }

    @GetMapping("/home")
    public String home(){
        return "home";
    }

    @GetMapping("/book/{id}")
    public String bookingPage(@PathVariable("id") int id, Model model){
        model.addAttribute("package",packService.packById(id));
        return "booking";
    }

    @PostMapping("/packBook")
    public String packBooking(
            @RequestParam("package_id") int pId,
            @RequestParam("totalPerson") int count,
            @RequestParam("travelDate")
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
            @CookieValue(value = "email",required = false) String email) {

        bookingService.savePack(pId, count, date,email);
        return "home";
    }

}
