import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import package1.package2.Message;
import org.json.JSONArray;
import org.json.JSONObject;

public class CricketMatchesAssignment {

    public static void main(String[] args) {
        try {
            // Make HTTP GET request to the API
            String apiUrl = "https://api.cuvora.com/car/partner/cricket-data";
            String apiKey = "test-creds@2320";
            String jsonResponse = sendGetRequest(apiUrl, apiKey);

            // Parse JSON response
            JSONArray matches = new JSONArray(jsonResponse);

            // Variables to store results
            int highestScore = Integer.MIN_VALUE;
            String highestScorerTeam = "";
            int matchesWith300PlusScore = 0;

            // Iterate through matches
            for (int i = 0; i < matches.length(); i++) {
                JSONObject match = matches.getJSONObject(i);

                // Get scores for both teams
                int team1Score = match.getInt("t1s");
                int team2Score = match.getInt("t2s");

                // Find highest score
                if (team1Score > highestScore) {
                    highestScore = team1Score;
                    highestScorerTeam = match.getString("t1");
                }
                if (team2Score > highestScore) {
                    highestScore = team2Score;
                    highestScorerTeam = match.getString("t2");
                }

                // Check if total score of both teams is 300 or more
                if (team1Score + team2Score >= 300) {
                    matchesWith300PlusScore++;
                }
            }

            // Print results
            System.out.println("Highest Score : Highest Score " + highestScore + " and Team Name is : " + highestScorerTeam);
            System.out.println("Number Of Matches with total 300 Plus Score : " + matchesWith300PlusScore);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Function to send HTTP GET request
    private static String sendGetRequest(String apiUrl, String apiKey) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("apiKey", apiKey);

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        connection.disconnect();
        return response.toString();
    }
}
