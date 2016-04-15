package ru.stqa.ptf.mantis.appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Created by Olga on 29.02.2016.
 */
public class ApplicationManager {

    private final Properties properties;
    //делаем private чтоб никто случайно не обратился к драйверу
    private WebDriver driver;
    private String browser;
    //создаем поле чтоб организовать ленивую инициализацию
    private RegistrationHelper registrationHelper;
    //создаем поле чтоб организовать ленивую инициализацию
    private FtpHelper ftpHelper;
    //создаем поле чтоб организовать ленивую инициализацию
    private MailHelper mailHelper;
    //создаем поле чтоб организовать ленивую инициализацию
    private DbHelper dbHelper;
    //создаем поле чтоб организовать ленивую инициализацию
    private UserHelper userHelper;
    public NavigationHelper navigationHelper;


    public ApplicationManager(String browser) {
        this.browser = browser;
        properties = new Properties();
    }

    //метод init теперь только загружает конфигурационный файл
    public void init() throws IOException {
        String target = System.getProperty("target", "local");
        properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
    }

    public void stop() {
        //останов только если браузер был инициализирован
        if (driver != null) {
            driver.quit();
        }
    }

    //инициализация HttpSession (он легковесный) при каждом обращении, чтоб открывать сколько угодно сессий
    public HttpSession newSession() {
        //передаем в конструктор объект ApplicationManager чтоб каждый раз не думать
        //какие именно данные должен ApplicationManager передать помощнику, он передает ссылку на самого себя
        return new HttpSession(this);
    }

    //метод для получения свойства конфигурационного файла
    //key - имя свойства, которое нужно получить у ApplicationManager
    public String getProperty(String key) {
        //возвращаем значение св-ва из конфигурационного файла
        return properties.getProperty(key);
    }

    public DbHelper db() {
        if (dbHelper == null) {
            dbHelper = new DbHelper();
        }
        return dbHelper;
    }

    public NavigationHelper getNavigationHelper() {
        if (navigationHelper == null) {
            navigationHelper = new NavigationHelper(this);
        }
        return navigationHelper;
    }

    public UserHelper getUserHelper() {
        if (userHelper == null) {
            userHelper = new UserHelper(this);
        }
        return userHelper;
    }
    //метод возвращает объект типа RegistrationHelper, для обращения к RegistrationHelper через ApplicationManager
    public RegistrationHelper registration() {
        //инициализируем RegistrationHelper только при первом обращении к этому методу
        if (registrationHelper == null) {
            //передаем хелперу ссылку на ApplicationManager
            // ApplicationManager нанимает помощника и передает ему ссылку на самого себя
            registrationHelper = new RegistrationHelper(this);
        }
        return registrationHelper;
    }

    //метод возвращает объект типа MailHelper, для обращения к MailHelper через ApplicationManager
    public MailHelper mail() {
        //инициализируем MailHelper только при первом обращении к этому методу
        if (mailHelper == null) {
            //передаем хелперу ссылку на ApplicationManager
            // ApplicationManager нанимает помощника и передает ему ссылку на самого себя
            mailHelper = new MailHelper(this);
        }
        return mailHelper;
    }

    //метод возвращает объект типа FtpHelper, для обращения к FtpHelper через ApplicationManager
    public FtpHelper ftp() {
        //инициализируем FtpHelper только при первом обращении к этому методу
        if (ftpHelper == null) {
            //передаем хелперу ссылку на ApplicationManager
            // ApplicationManager нанимает помощника и передает ему ссылку на самого себя
            ftpHelper = new FtpHelper(this);
        }
        return ftpHelper;
    }

    //делаем ленивую инициализацию драйвера в момент первого обращения
    public WebDriver getDriver() {
        //инициализируем браузер только если он еще не инициализирован
        if (driver == null) {
            if (browser.equals(BrowserType.FIREFOX)) {
                driver = new FirefoxDriver();
            } else if (browser.equals(BrowserType.CHROME)) {
                driver = new ChromeDriver();
            } else if (browser.equals(BrowserType.IE)) {
                driver = new InternetExplorerDriver();
            }
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            driver.get(properties.getProperty("web.baseUrl"));
        }
        return driver;
    }
}
