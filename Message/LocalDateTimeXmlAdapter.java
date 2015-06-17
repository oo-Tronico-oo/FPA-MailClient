/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Message;

import java.time.LocalDateTime;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author Tronico
 */
public class LocalDateTimeXmlAdapter extends XmlAdapter<String, LocalDateTime> {
    @Override
    public String marshal(LocalDateTime arg0) throws Exception {
        return arg0.toString();
    }

    @Override
    public LocalDateTime unmarshal(String arg) throws Exception {
        return LocalDateTime.parse(arg);
    }
}
