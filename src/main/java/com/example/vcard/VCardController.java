package com.example.vcard;

import ezvcard.Ezvcard;
import ezvcard.VCard;
import ezvcard.VCardVersion;
import ezvcard.property.StructuredName;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class VCardController {
    String addrString = "http://localhost:8080/generate";

    @GetMapping("/search")
    public ResponseEntity<String> search(@RequestParam String call) throws IOException {
        Document doc = Jsoup.connect("https://www.pkt.pl/szukaj/" + call).get();
        Elements elements = doc.select(".box-content.company-row.list-sel");

        StringBuilder result = new StringBuilder();
        List<CustomVCard> customVCards = new ArrayList<>();
        for (Element element : elements) {
            CustomVCard customVCard = new CustomVCard();
            customVCards.add(customVCard);
            customVCard.FN = element.select(".company-name").text();
            customVCard.TEL = element.select(".phone-content").text();
            customVCard.URL = element.select(".www--full").text();
            customVCard.EMAIL = element.select("a.popup span").attr("title");
        }

        for (CustomVCard node : customVCards) {
            result.append(node.FN).append(" <a href=").append("\"").append(addrString).append("?").append("fn=").append(node.FN).append("&").append("tel=").append(node.TEL).append("&").append("url=").append(node.URL).append("&").append("email=").append(node.EMAIL).append("\"").append("><button>Generate VCard</button></a> <br>");
        }
        return ResponseEntity.ok(result.toString());
    }

    @GetMapping("/generate")
    public ResponseEntity<Resource> generate(@RequestParam String fn, @RequestParam String tel,
                                             @RequestParam String url, @RequestParam String email) throws IOException {
        VCard vcard = new VCard();
        vcard.setFormattedName(fn);
        vcard.addTelephoneNumber(tel);
        vcard.addUrl(url);
        vcard.addEmail(email);

        File file = new File("VCard.vcf");
        Ezvcard.write(vcard).version(VCardVersion.V4_0).go(file);

        InputStreamResource resource = new InputStreamResource(new FileInputStream("VCard.vcf"));
        return ResponseEntity
                .ok()
                .header("Content-Disposition", "attachment; filename=" + fn + ".vcf")
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("text/vcf")).body(resource);
    }


}
