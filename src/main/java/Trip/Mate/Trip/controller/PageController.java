package Trip.Mate.Trip.controller;

import Trip.Mate.Trip.dto.HotelDto;
import Trip.Mate.Trip.dto.PackageDto;
import Trip.Mate.Trip.model.Hotel;
import Trip.Mate.Trip.model.User;
import Trip.Mate.Trip.service.HotelService;
import Trip.Mate.Trip.service.PackService;
import Trip.Mate.Trip.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Controller
@RequiredArgsConstructor
public class PageController {

    private final  UserService userService;
    private final HotelService hotelService;
    private final PackService packService;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/login")
    public String UserLogin(@RequestParam("username") String email,@RequestParam("password") String pass){
        System.out.println(email + pass);
        User user = userService.verifyUser(email, pass);
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

}
