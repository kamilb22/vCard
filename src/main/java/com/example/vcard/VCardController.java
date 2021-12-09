package com.example.vcard;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class VCardController {
    String addrString = "http://localhost:8080/generate";

    @GetMapping("/search")
    public ResponseEntity<String> search(@RequestParam String call) throws IOException {
        System.out.println("https://www.pkt.pl/szukaj/" + call);
        Document doc = Jsoup.connect("https://www.pkt.pl/szukaj/" + call).get();
        Elements elements = doc.select(".box-content.company-row.list-sel");

        String result = "<meta name=\\\"viewport\\\" content=\\\"width=device-width, initial-scale=1.0\\\">";
        List<VCard> vCards = new ArrayList<>();
        for (Element element:
             elements) {
            VCard vCard = new VCard();
            vCards.add(vCard);
            vCard.FN = element.select(".company-name").text();
            vCard.TEL = element.select(".phone-content").text();
            vCard.URL = element.select(".www--full").text();
            vCard.EMAIL = element.select("a.popup span").attr("title");
        }

        for (VCard node :
                vCards) {
            result += node.FN + " <button action=" + "\"" + addrString + "&" + "fn=" + node.FN+ "&" + "tel=" + node.TEL+ "&" + "url=" + node.URL+ "&" + "email=" + node.EMAIL + "\"" + ">Generate VCard</button> <br>";
        }
        return ResponseEntity.ok(result);
    }


}
