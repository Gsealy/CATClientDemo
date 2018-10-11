package io.github.gsealy;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Message;
import com.dianping.cat.message.Transaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Gsealy
 */
@RestController
@SpringBootApplication
public class WebApplication {

  public static void main(String[] args) {
    SpringApplication.run(WebApplication.class, args);
  }

  /**
   * 监控访问id
   */
  @GetMapping("/index/{id}")
  public String index(@PathVariable int id) {
    Transaction t = Cat
        .getProducer().newTransaction("URL", "Get.id");
    try {
      Cat.getProducer().newEvent("URL.Get", "id").setStatus(Message.SUCCESS);
      t.addData("id is:" + id);
      // 你的业务代码
      System.out.println("id is : " + id);

      t.setStatus(Transaction.SUCCESS);
    } catch (Exception e) {
      t.setStatus(e);
      throw e;
    } finally {
      t.complete();
    }
    return "id: " + id;
  }
}
