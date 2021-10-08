package com.anhnc2.ehealicords.api;

//import com.anhnc2.ehealicords.constant.StatusCode;
//import com.anhnc2.ehealicords.data.common.PatientRelative;
//import com.anhnc2.ehealicords.data.entity.UserRelativeEntity;
//import com.anhnc2.ehealicords.data.response.HttpResponse;
//import com.anhnc2.ehealicords.data.response.HttpResponseImpl;
//import com.anhnc2.ehealicords.data.response.LiteUserRelative;
//import com.anhnc2.ehealicords.data.response.PaginationResponse;
//import com.anhnc2.ehealicords.service.user.UserService;
//import java.util.List;
//import java.util.stream.Collectors;
//import lombok.AllArgsConstructor;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/protected/user-relatives")
//@AllArgsConstructor
//public class UserRelativeApi {
//    private final UserService userService;
//
//    @PostMapping
//    public HttpResponse<Object> createRelative(@RequestBody PatientRelative patientRelative) {
//        Long id = userService.createNewRelative(patientRelative.toPatientRelative());
//        return HttpResponseImpl.builder().code(StatusCode.SUCCESS).data(id).build();
//    }
//
//    @GetMapping
//    public HttpResponse<List<PatientRelative>> getRelatives() {
//        return HttpResponseImpl.<List<PatientRelative>>builder()
//                .code(StatusCode.SUCCESS)
//                .data(
//                        userService.getAllRelatives().stream()
//                                .map(PatientRelative::fromEntity)
//                                .collect(Collectors.toList()))
//                .build();
//    }
//
//    @PutMapping("/{id}")
//    public HttpResponse<Long> updateRelative(
//            @RequestBody PatientRelative patientRelative, @PathVariable("id") Long id) {
//        UserRelativeEntity userRelativeDAO = patientRelative.toPatientRelative();
//        userRelativeDAO.setId(id);
//        userService.updateRelative(userRelativeDAO);
//        return HttpResponseImpl.<Long>builder().code(StatusCode.SUCCESS).build();
//    }
//
//    @DeleteMapping("/{id}")
//    public HttpResponse<Object> removeRelativePerson(@PathVariable("id") Long id) {
//        userService.deleteRelative(id);
//        return HttpResponseImpl.builder().code(StatusCode.SUCCESS).build();
//    }
//
//    @GetMapping("/{id}")
//    public HttpResponse<UserRelativeEntity> getRelativePerson(@PathVariable("id") Long id) {
//        return HttpResponseImpl.success(userService.getRelative(id));
//    }
//
//    @GetMapping("/all")
//    public HttpResponse<PaginationResponse<List<LiteUserRelative>>> getAll(
//            @RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize) {
//
//        return HttpResponseImpl.success(userService.getAllRelativeUsers(page, pageSize));
//    }
//}
