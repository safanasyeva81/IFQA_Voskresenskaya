package hw5;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;


import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthTest {

    private static final String BASE_URL = "http://localhost:8080";
    private static final String CREDENTIALS_FILE = "src/test/resources/user_credentials.json";

    private static String authToken;

    private static String originalJson;

    @BeforeAll
    static void setUp() throws Exception {
        originalJson = new String(Files.readAllBytes(Paths.get(CREDENTIALS_FILE)));
    }

    @Test
    @Order(1)
    public void step1_registration() {
        File credentialsFile = new File(CREDENTIALS_FILE);
        RestAssured
                .given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .body(credentialsFile) // Отправляем САМ ФАЙЛ
                .log().all()
                .when()
                .post("/api/register")
                .then()
                .statusCode(200)
                .log().all()
                .body(equalTo("success register"));
        System.out.println("Регистрация прошла успешно!");
    }

    @Test
    @Order(2)
    public void step2a_login_userNotFound() throws Exception {
        String originalJson = new String(Files.readAllBytes(Paths.get(CREDENTIALS_FILE)));
        JsonPath jsonPath = new JsonPath(originalJson);
        String originalUsername = jsonPath.getString("username");
        String nonExistentUsername = "non_existent_user_" + System.currentTimeMillis();
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> credentials = objectMapper.readValue(originalJson, Map.class);

        credentials.put("username", nonExistentUsername);
        String modifiedJson = objectMapper.writeValueAsString(credentials);

        System.out.println("Изменённый JSON:" + modifiedJson);
        System.out.println("Исходный логин:" + originalUsername);
        System.out.println("Подставленный логин:" + nonExistentUsername);

        RestAssured
                .given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .body(modifiedJson)
                .log().all()
                .when()
                .post("/api/login")
                .then()
                .statusCode(401)
                .log().all()
                .body(equalTo("not found"));

        System.out.println("Тест 'пользователь не найден' пройден");
    }

    @Test
    @Order(3)
    public void step2b_login_wrongPassword() throws Exception {
        String originalJson = new String(Files.readAllBytes(Paths.get(CREDENTIALS_FILE)));
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> credentials = objectMapper.readValue(originalJson, Map.class);
        String wrongPassword = "wrong_password_" + System.currentTimeMillis();
        credentials.put("password", wrongPassword);
        String modifiedJson = objectMapper.writeValueAsString(credentials);
        System.out.println("Изменённый JSON (неверный пароль): " + modifiedJson);

        RestAssured
                .given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .body(modifiedJson)  // ← ИЗМЕНЁННЫЙ JSON
                .log().all()
                .when()
                .post("/api/login")
                .then()
                .statusCode(401)
                .log().all()
                .body(equalTo("not right pass"));

        System.out.println("Тест 'неверный пароль' пройден");
    }
    @Test
    @Order(4)
    public void step2c_login_success() {
        Response response = RestAssured
                .given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .body(originalJson)
                .log().all()
                .when()
                .post("/api/login")
                .then()
                .statusCode(200)
                .log().all()

.body(startsWith("token : "))
                .extract().response();

        String responseBody = response.getBody().asString();
        authToken = responseBody.replace("token : ", "").trim();

        System.out.println("Авторизация успешна!");
        System.out.println("Получен токен:" + authToken);
    }

    @Test
    @Order(5)
    public void step3a_logout_invalidToken() {
        String invalidToken = "invalid-token-12345";

        RestAssured
                .given()
                .baseUri(BASE_URL)
                .header("Authorization", invalidToken)
                .log().all()
                .when()
                .get("/api/logout")
                .then()
                .statusCode(400)
                .log().all()
                .body("status", equalTo(400))
                .body("error", equalTo("Bad Request"));

        System.out.println("Тест 'неверный токен' пройден");
    }

    @Test
    @Order(6)
    public void step3b_logout_success() {
        Assertions.assertNotNull(authToken, "Токен не был получен");

        RestAssured
                .given()
                .baseUri(BASE_URL)
                .header("Authorization", authToken)
                .log().all()
                .when()
                .get("/api/logout")
                .then()
                .statusCode(200)
                .log().all()
                .body(equalTo("success logout"));

        System.out.println("Выход выполнен успешно!");
    }
}