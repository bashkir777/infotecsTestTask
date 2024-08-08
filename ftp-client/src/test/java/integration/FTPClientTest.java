package integration;

import com.bashkir777.entities.Student;
import com.bashkir777.interfaces.FTPClient;
import com.bashkir777.services.FTPClientActive;
import com.bashkir777.services.FTPClientPassive;
import com.bashkir777.services.StudentService;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.concurrent.*;

public class FTPClientTest {
    private final String TEST_REMOTE_FILE_ACTIVE = "testRemoteFileActive.txt";
    private final String TEST_REMOTE_FILE_PASSIVE = "testRemoteFilePassive.txt";
    private final StudentService studentService = new StudentService();

    @BeforeClass
    public void setUpStudentService(){
        studentService.addStudent(new Student(1, "student_1"));
        studentService.addStudent(new Student(2, "student_2"));
        studentService.addStudent(new Student(3, "student_3"));
    }

    @DataProvider(name = "ftpClients")
    public Object[][] createFTPClients() {
        return new Object[][] {
                { new FTPClientPassive(), TEST_REMOTE_FILE_PASSIVE},
                { new FTPClientActive(), TEST_REMOTE_FILE_ACTIVE }
        };
    }

    @Test(groups= {"integration", "upload"}, dataProvider = "ftpClients"
            , description = "FTPClientActive and FTPClientPassive correctly upload file to ftp-server")
    public void ftpClientSuccessfullyUploadsFile(FTPClient ftpClient, String remoteFileName) {
        Callable<Void> task = () -> {
            ftpClient.uploadFile(studentService.queueToString(), remoteFileName);
            return null;
        };

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Void> future = executor.submit(task);

        try {
            future.get(2, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            future.cancel(true);
            Assert.fail("Upload file operation timed out.");
        } catch (ExecutionException e) {
            Assert.fail("Error during file upload.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            Assert.fail("File upload was interrupted.");
        } finally {
            executor.shutdownNow();
        }
    }

    @Test(groups= {"integration"}, dependsOnGroups = "upload", dataProvider = "ftpClients"
            , description = "FTPClientActive and FTPClientPassive correctly download file from ftp-server")
    public void ftpClientsSuccessfullyDownloadFile(FTPClient ftpClient, String remoteFileName) {

        Callable<String> task = () -> ftpClient.downloadFile(remoteFileName);

        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<String> future = executor.submit(task);

        try {
            String data = future.get(2, TimeUnit.SECONDS);
            String expectedData = studentService.queueToString();
            studentService.clean();
            studentService.uploadQueue(data);
            Assert.assertEquals(studentService.queueToString(), expectedData);
        } catch (TimeoutException e) {
            future.cancel(true);
            Assert.fail("Upload file operation timed out.");
        } catch (ExecutionException e) {
            Assert.fail("Error during file upload.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            Assert.fail("File upload was interrupted.");
        } finally {
            executor.shutdownNow();
        }
    }

}
