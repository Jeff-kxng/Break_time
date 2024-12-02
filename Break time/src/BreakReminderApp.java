import java.util.Timer;
import java.util.TimerTask;
import java.util.List;
import java.util.Arrays;
import java.util.Random;
import java.awt.AWTException;
import java.awt.Robot;

public class BreakReminderApp {
    private int interval; // Khoảng thời gian giữa các lần nhắc (giây)
    private int breakDuration; // Thời gian nghỉ ngơi (giây)
    private Robot robot;

    public BreakReminderApp(int interval, int breakDuration) {
        this.interval = interval;
        this.breakDuration = breakDuration;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                showReminder();
                lockInput();
                suggestActivity();
                try {
                    Thread.sleep(breakDuration * 1000); // Nghỉ trong thời gian quy định
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                unlockInput();
            }
        }, 0, interval * 1000);
    }

    private void showReminder() {
        System.out.println("Đã đến lúc nghỉ ngơi! Hãy thư giãn trong vài phút.");
    }

    private void lockInput() {
        System.out.println("Khóa tạm thời bàn phím và chuột. Vui lòng nghỉ ngơi.");
        for (int i = 0; i < breakDuration * 20; i++) { // Khóa trong khoảng thời gian nghỉ
            robot.mouseMove(0, 0);
            robot.delay(100);
        }
    }

    private void unlockInput() {
        System.out.println("Đã hết thời gian nghỉ ngơi. Mở khóa bàn phím và chuột.");
    }

    private void suggestActivity() {
        List<String> activities = Arrays.asList(
                "Nghe nhạc thư giãn.",
                "Tập thể dục nhẹ nhàng.",
                "Thực hiện bài tập thở sâu."
        );
        Random random = new Random();
        String suggestion = activities.get(random.nextInt(activities.size()));
        System.out.println("Gợi ý: " + suggestion);
    }

    public static void main(String[] args) {
        int interval = 3600;
        int breakDuration = 5;
        BreakReminderApp app = new BreakReminderApp(interval, breakDuration);
        app.start();
    }
}  