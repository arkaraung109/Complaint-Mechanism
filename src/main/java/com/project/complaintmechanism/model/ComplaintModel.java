package com.project.complaintmechanism.model;

import com.project.complaintmechanism.customValidator.NonRequiredImage;
import com.project.complaintmechanism.customValidator.RequiredImage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComplaintModel {

    private long id;

    @NotBlank(message = "ကျေးဇူးပြု၍ တိုင်ကြားမှုအကြောင်းအရာကို ရေးသားပေးပါ။")
    @Pattern(regexp = "^.{0,700}$", message = "စာလုံးရေ ၇၀၀ အောက်အတွင်းသာ ရေးသားပေးပါ။")
    private String description;

    @NotBlank(message = "ကျေးဇူးပြု၍ တိုင်ကြားမှုအကြောင်းအရာကို ရေးသားပေးပါ။")
    @Pattern(regexp = "^.{0,700}$", message = "စာလုံးရေ ၇၀၀ အောက်အတွင်းသာ ရေးသားပေးပါ။")
    private String remark;

    private LocalDateTime submittedAt;

    private boolean readStatus;

    @NotBlank(message = "ကျေးဇူးပြု၍ ကျား/မ ကို ရွေးခြယ်ပေးပါ။")
    private int acceptedStatus;

    @NotBlank(message = "ကျေးဇူးပြု၍ ကျား/မ ကို ရွေးခြယ်ပေးပါ။")
    private boolean solvedStatus;

    private boolean tempDeletedStatus;

    @NotBlank(message = "ကျေးဇူးပြု၍ နာမည်ကို ရေးသားပေးပါ။")
    @Pattern(regexp = "^([^<>~`!\\[\\]{}|@#^*+=:;/?%$\"\\\\]){0,100}$", message = "ကျေးဇူးပြု၍ အထူးသီးသန့်စာလုံးများ မပါဝင်အောင် ရေးသားပေးပါ။")
    private String name;

    @NotBlank(message = "ကျေးဇူးပြု၍ ကျား/မ ကို ရွေးခြယ်ပေးပါ။")
    private String gender;

    @NotBlank(message = "ကျေးဇူးပြု၍ ဖုန်းနံပါတ်ကို ရေးသားပေးပါ။")
    @Pattern(regexp = "^(09-[0-9]{7,9})|(09\\s*[0-9]{7,9})|(\\s*)|(\u1040\u1049-[\u1040-\u1049]{7,9})|(\u1040\u1049\\s*[\u1040-\u1049]{7,9})$", message = "ကျေးဇူးပြု၍ မှန်ကန်သောဖုန်းနံပါတ်ကိုသာ ရေးသားပေးပါ။")
    private String phone;

    @NotBlank(message = "ကျေးဇူးပြု၍ အီးမေးလ်ကို ရေးသားပေးပါ။")
    @Pattern(regexp = "^([\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4})|(\\s*)$", message = "ကျေးဇူးပြု၍ မှန်ကန်သောအီးမေးလ်ကိုသာ ရေးသားပေးပါ။")
    private String email;

    @RequiredImage
    private MultipartFile idCardFront;

    @RequiredImage
    private MultipartFile idCardBack;

    @RequiredImage
    private MultipartFile ecPhoto1;

    @NonRequiredImage
    private MultipartFile ecPhoto2;

    @Positive(message = "ကျေးဇူးပြု၍ မြို့ကို ရွေးခြယ်ပေးပါ။")
    private long cityId;

    @Positive(message = "ကျေးဇူးပြု၍ မြို့နယ်ကို ရွေးခြယ်ပေးပါ။")
    private long townshipId;

    @Positive(message = "ကျေးဇူးပြု၍ စက်မှုဇုန်ကို ရွေးခြယ်ပေးပါ။")
    private long industrialZoneId;

    @Positive(message = "ကျေးဇူးပြု၍ ကုမ္ပဏီကို ရွေးခြယ်ပေးပါ။")
    private long companyId;

    @Positive(message = "ကျေးဇူးပြု၍ တိုင်ကြားမှုခေါင်းစဉ်ကို ရွေးခြယ်ပေးပါ။")
    private long complaintTitleId;

}
