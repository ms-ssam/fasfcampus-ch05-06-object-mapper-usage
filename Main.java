import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dto.Car;
import dto.User;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String args[]) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        /*
         json의 기본 인코딩이 utf-8이기 때문에 file -> setting -> file encoding에서
         utf-8 설정, help -> find action -> edit custom VM option에서 utf-8 설정 필요
         */
        User user = new User();
        user.setName("홍길동");
        user.setAge(10);

        Car car1 = new Car();
        car1.setName("K5");
        car1.setCarNumber("11가 1111");
        car1.setType("sedan");

        Car car2 = new Car();
        car2.setName("Q5");
        car2.setCarNumber("11나 1111");
        car2.setType("SUV");

        List<Car> carList = Arrays.asList(car1, car2);
        user.setCars(carList);

        //System.out.println(user);

        String json = objectMapper.writeValueAsString(user);
        System.out.println(json + "\n");

        // Json node 접근
        JsonNode jsonNode = objectMapper.readTree(json);

        // Json에서 일반 value의 경우 값 가져오기
        String _name = jsonNode.get("name").asText();
        int _age = jsonNode.get("age").asInt();

        // Json에서 List value의 경우 값 가져오기
        JsonNode cars = jsonNode.get("cars");  // list 자체가 하나의 Json node
        ArrayNode arrayNode = (ArrayNode)cars;
        List<Car> _cars = objectMapper.convertValue(arrayNode, new TypeReference<List<Car>>() {});

        System.out.println("name : " + _name);
        System.out.println("age : " + _age);
        System.out.println("cars : " + _cars);
        System.out.println();

        // Json node 접근해서 값 변경하기
        ObjectNode objectNode = (ObjectNode)jsonNode;
        objectNode.put("name", "steve");
        objectNode.put("age", 20);
        System.out.println(objectNode.toPrettyString());
    }
}
