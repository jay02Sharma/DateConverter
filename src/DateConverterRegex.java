import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateConverterRegex
{

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);

        try
        {
            // User input prompt
            System.out.println("Enter month (e.g., 'Jan' or 'January'), day, week, quarter, or 'current': ");
            String input = scanner.nextLine().trim();

            // Get current date
            LocalDate currentDate = LocalDate.now();
            LocalDate startDate = null, endDate = null;

            // Regex pattern to match month input
            String monthRegex = "(?i)(jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec)";
            Pattern pattern = Pattern.compile(monthRegex);
            Matcher matcher = pattern.matcher(input);

            // Check if input matches a month abbreviation or name
            if (matcher.find())
            {
                String monthStr = matcher.group(1);
                Month month = null;
                // Match month abbreviation or name with Java Month enum
                for (Month m : Month.values())
                {
                    if (m.name().toLowerCase().startsWith(monthStr))
                    {
                        month = m;
                        break;
                    }
                }
                // If month found, calculate start and end date of the month
                if (month != null)
                {
                    startDate = LocalDate.of(currentDate.getYear(), month, 1);
                    endDate = startDate.plusMonths(1).minusDays(1);
                    // Print start and end date of the month
                    System.out.println("Start Date of " + month.getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " = " + startDate);
                    System.out.println("End Date of " + month.getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " = " + endDate);
                }
                else
                {
                    // If invalid month input
                    System.out.println("Invalid month input. Please enter a valid month name or abbreviation.");
                }
            }
            else
            {
                // Handling other input options
                switch (input)
                {
                    case "current year":
                        startDate = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), 1);
                        endDate = startDate.plusYears(1).minusDays(1);
                        break;
                    case "current day":
                        startDate = endDate = currentDate;
                        break;
                    case "current week":
                        startDate = currentDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
                        endDate = startDate.plusDays(6);
                        break;
                    case "current quarter":
                        int currentMonth = currentDate.getMonthValue();
                        int currentQuarter = (currentMonth - 1) / 3 + 1;
                        startDate = LocalDate.of(currentDate.getYear(), (currentQuarter - 1) * 3 + 1, 1);
                        endDate = startDate.plusMonths(2).with(TemporalAdjusters.lastDayOfMonth());
                        break;
                    case "previous year":
                        int previousYear = currentDate.getYear() - 1;
                        Month startMonthOfPreviousYear = currentDate.getMonth();
                        startDate = LocalDate.of(previousYear, startMonthOfPreviousYear, 1);
                        endDate = startDate.plusYears(1).minusDays(1);
                        break;
                    case "previous day":
                        startDate = endDate = currentDate.minusDays(1);
                        break;
                    case "previous week":
                        startDate = currentDate.minusWeeks(1).with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
                        endDate = startDate.plusDays(6);
                        break;
                    case "previous month":
                        startDate = LocalDate.of(currentDate.minusMonths(1).getYear(), currentDate.minusMonths(1).getMonth(), 1);
                        endDate = startDate.plusMonths(1).minusDays(1);
                        break;
                    case "previous quarter":
                        int previousQuarter = (currentDate.getMonthValue() - 1) / 3 - 1;
                        if (previousQuarter < 0) previousQuarter = 3 + previousQuarter;
                        startDate = LocalDate.of(currentDate.getYear(), previousQuarter * 3 + 1, 1);
                        endDate = startDate.plusMonths(2).with(TemporalAdjusters.lastDayOfMonth());
                        break;
                    default:
                        System.out.println("Invalid input. Please enter a valid month, day, week, quarter, or 'current'.");
                        break;
                }

                // Print start and end date if valid input
                if (startDate != null && endDate != null)
                {
                    System.out.println("Start Date = " + startDate);
                    System.out.println("End Date = " + endDate);
                }
            }
        }
        catch (Exception e)
        {
            // Catch any exceptions
            System.out.println("Error occurred: " + e.getMessage());
        }
        finally
        {
            // Close scanner
            scanner.close();
        }
    }
}
