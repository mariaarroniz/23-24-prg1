class Building {
    public static void main(String[] args) {

        final int END_OF_WEEK = 7;
        final int HOURS_PER_DAY = 24;
        final int MAX_FLOORS = 7;
        final int MAX_WINDOWS = 6;
        int column = 0;
        int row = 0;

        final double PROB_WINDOW_OPENED = 0.7;
        final double PROB_LIGHTS_ON = 0.6;
        final double PROB_LIGHTING = 0.2;
        final double PROB_MAINTENANCE = 0.05;

        boolean isOpen = false;
        boolean isOn = false;

        int consumePerHour = 0;
        int dailyConsume = 0;

        final String basement = """
                :[       ]::::|      |::::[       ]:
                ------------------------------------
                ==========================
                ==============================
                ==================================

                """;
        final String TIP = "     ".repeat(3) + "__/\\__";
        final String HEADER = "|####|";
        final String ANTENNA = "  |  ";
        final String ROOF = "=".repeat(36);

        for (int day = 1; day < END_OF_WEEK; day++) {
            for (int hour = 0; hour < HOURS_PER_DAY; hour++) {

                dailyConsume = dailyConsume + consumePerHour;
                consumePerHour = 0;

                boolean lightingStrikes = false;
                boolean doingMaintenance = false;
                double lighting = Math.random();
                double maintenance = Math.random();

                if (!lightingStrikes && lighting <= PROB_LIGHTING) {
                    column = (int) (Math.random() * 6);
                    lightingStrikes = true;
                }
                if (!doingMaintenance && maintenance <= PROB_MAINTENANCE) {
                    row = (int) (Math.random() * 6);
                    doingMaintenance = true;
                }

                cleanScreen();
                System.out.println(TIP);
                System.out.println(ANTENNA.repeat(3) + HEADER + ANTENNA.repeat(3));
                System.out.println(ROOF);

                for (int floor = 0; floor < MAX_FLOORS; floor++) {
                    for (int window = 0; window < MAX_WINDOWS; window++) {

                        double windowOpen = Math.random();
                        double lightsOn = Math.random();

                        if (windowOpen <= PROB_WINDOW_OPENED) {
                            isOpen = true;
                        } else {
                            isOpen = false;
                        }
                        if (lightsOn <= PROB_LIGHTS_ON) {
                            isOn = true;
                            consumePerHour++;
                        } else {
                            isOn = false;
                        }

                        String theWindow = printWindow(isOn, isOpen);

                        if (window == column) {
                            theWindow = ":[X]:";
                        }
                        if (floor == row) {
                            theWindow = ":[#]:";
                        }

                        System.out.print(theWindow);

                    }
                    System.out.println();
                }
                System.out.print(basement);

                printStatistics(day, hour, consumePerHour, column, row, dailyConsume);

                pause(1);
            }
        }
    }

    private static String printWindow(boolean isOn, boolean isOpen) {
        if (!isOpen) {
            return ":[ ]:";
        } else if (isOn) {
            return ":[*]:";
        }
        return ":[']:";

    }

    public static void printStatistics(int day, int hour, int consumePerHour, int column, int row, int dailyConsume) {
        System.out.println("Día " + day + " - " + hour + ":00h Consumo hora: " + consumePerHour);
        System.out.print("CONSUMO TOTAL DURANTE LOS DIAS: D" + day + ": " + dailyConsume + "| ");
    }

    public static void cleanScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void pause(int segundos) {
        try {
            Thread.sleep(1000 * segundos);
        } catch (InterruptedException e) {
        }
    }
}
