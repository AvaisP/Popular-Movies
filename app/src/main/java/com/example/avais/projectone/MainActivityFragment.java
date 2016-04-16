package com.example.avais.projectone;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    public MainActivityFragment() {
    }
    private String key = BuildConfig.API_KEY;
    public static final int UPDATE_SETTINGS_REQUEST = 1;
    URL URL;
    private PosterAdapter posterAdapter;
    private Movie[] Movies;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    public interface Callback {

        /**
         * Called when an item is selected in the gridview
         *
         * @param movie the selected movie
         */
        void onItemSelected(Movie movie);
    }

        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        posterAdapter = new PosterAdapter(getActivity(), R.layout.grid_item, new ArrayList<Movie>());
        GridView gv = (GridView) rootView.findViewById(R.id.grid_view);
        gv.setAdapter(posterAdapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, final View view,
                                    final int position, final long id) {
                final Movie movie = posterAdapter.getItem(position);
                ((Callback) getActivity()).onItemSelected(movie);
            }
        });
        getMovies();
        return rootView;
    }


    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        final int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivityForResult(new Intent(getActivity(), SettingsActivity.class),
                    UPDATE_SETTINGS_REQUEST);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == UPDATE_SETTINGS_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                // If the update settings request is ok, refresh movies
                getMovies();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getMovies();  //refresh the page
    }

    void getMovies()
    {
        SharedPreferences prefs =
                PreferenceManager.getDefaultSharedPreferences(getActivity());
        String showBy = prefs.getString(getString(R.string.pref_sort_order_key),
                getString(R.string.pref_sort_order_default));
        Uri.Builder builder = new Uri.Builder();
        try {
            builder.scheme("https").authority("api.themoviedb.org").appendPath("3").appendPath("movie")
                    .appendPath(showBy).appendQueryParameter("api_key", key);
            URL = new URL(builder.toString());
            new GetMoviesTask().execute(key);
        }catch (final MalformedURLException e) {
            Log.e("Malformed URL: ", e.getMessage());
        }
    }

    public class GetMoviesTask extends AsyncTask<String, Void, Movie[]>{

        private final String LOG_TAG = GetMoviesTask.class.getSimpleName();
        @Override
        protected Movie[] doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String JSON = "{\"page\":1,\"results\":[{\"poster_path\":\"\\/lIv1QinFqz4dlp5U4lQ6HaiskOZ.jpg\",\"adult\":false,\"overview\":\"Under the direction of a ruthless instructor, a talented young drummer begins to pursue perfection at any cost, even his humanity.\",\"release_date\":\"2014-10-10\",\"genre_ids\":[18,10402],\"id\":244786,\"original_title\":\"Whiplash\",\"original_language\":\"en\",\"title\":\"Whiplash\",\"backdrop_path\":\"\\/6bbZ6XyvgfjhQwbplnUh1LSj1ky.jpg\",\"popularity\":8.041335,\"vote_count\":1777,\"video\":false,\"vote_average\":8.35},{\"poster_path\":\"\\/9O7gLzmreU0nGkIB6K3BsJbzvNv.jpg\",\"adult\":false,\"overview\":\"Framed in the 1940s for the double murder of his wife and her lover, upstanding banker Andy Dufresne begins a new life at the Shawshank prison, where he puts his accounting skills to work for an amoral warden. During his long stretch in prison, Dufresne comes to be admired by the other inmates -- including an older prisoner named Red -- for his integrity and unquenchable sense of hope.\",\"release_date\":\"1994-09-10\",\"genre_ids\":[18,80],\"id\":278,\"original_title\":\"The Shawshank Redemption\",\"original_language\":\"en\",\"title\":\"The Shawshank Redemption\",\"backdrop_path\":\"\\/xBKGJQsAIeweesB79KC89FpBrVr.jpg\",\"popularity\":6.619824,\"vote_count\":4705,\"video\":false,\"vote_average\":8.27},{\"poster_path\":\"\\/d4KNaTrltq6bpkFS01pYtyXa09m.jpg\",\"adult\":false,\"overview\":\"The story spans the years from 1945 to 1955 and chronicles the fictional Italian-American Corleone crime family. When organized crime family patriarch Vito Corleone barely survives an attempt on his life, his youngest son, Michael, steps in to take care of the would-be killers, launching a campaign of bloody revenge.\",\"release_date\":\"1972-03-15\",\"genre_ids\":[18,80],\"id\":238,\"original_title\":\"The Godfather\",\"original_language\":\"en\",\"title\":\"The Godfather\",\"backdrop_path\":\"\\/6xKCYgH16UuwEGAyroLU6p8HLIn.jpg\",\"popularity\":5.390769,\"vote_count\":3163,\"video\":false,\"vote_average\":8.2},{\"poster_path\":\"\\/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg\",\"adult\":false,\"overview\":\"Interstellar chronicles the adventures of a group of explorers who make use of a newly discovered wormhole to surpass the limitations on human space travel and conquer the vast distances involved in an interstellar voyage.\",\"release_date\":\"2014-11-05\",\"genre_ids\":[12,18,878],\"id\":157336,\"original_title\":\"Interstellar\",\"original_language\":\"en\",\"title\":\"Interstellar\",\"backdrop_path\":\"\\/xu9zaAevzQ5nnrsXN6JcahLnG4i.jpg\",\"popularity\":14.627387,\"vote_count\":4741,\"video\":false,\"vote_average\":8.2},{\"poster_path\":\"\\/eqFckcHuFCT1FrzLOAvXBb4jHwq.jpg\",\"adult\":false,\"overview\":\"Jack is a young boy of 5 years old who has lived all his life in one room. He believes everything within it are the only real things in the world. But what will happen when his Ma suddenly tells him that there are other things outside of Room?\",\"release_date\":\"2015-10-16\",\"genre_ids\":[18,53],\"id\":264644,\"original_title\":\"Room\",\"original_language\":\"en\",\"title\":\"Room\",\"backdrop_path\":\"\\/tBhp8MGaiL3BXpPCSl5xY397sGH.jpg\",\"popularity\":6.260095,\"vote_count\":712,\"video\":false,\"vote_average\":8.19},{\"poster_path\":\"\\/3TpMBcAYH4cxCw5WoRacWodMTCG.jpg\",\"adult\":false,\"overview\":\"An urban office worker finds that paper airplanes are instrumental in meeting a girl in ways he never expected.\",\"release_date\":\"2012-11-02\",\"genre_ids\":[16,10751,10749],\"id\":140420,\"original_title\":\"Paperman\",\"original_language\":\"en\",\"title\":\"Paperman\",\"backdrop_path\":\"\\/cqn1ynw78Wan37jzs1Ckm7va97G.jpg\",\"popularity\":3.330358,\"vote_count\":386,\"video\":false,\"vote_average\":8.14},{\"poster_path\":\"\\/4mFsNQwbD0F237Tx7gAPotd0nbJ.jpg\",\"adult\":false,\"overview\":\"A true story of two men who should never have met - a quadriplegic aristocrat who was injured in a paragliding accident and a young man from the projects.\",\"release_date\":\"2011-11-02\",\"genre_ids\":[18,35],\"id\":77338,\"original_title\":\"Intouchables\",\"original_language\":\"fr\",\"title\":\"The Intouchables\",\"backdrop_path\":\"\\/ihWaJZCUIon2dXcosjQG2JHJAPN.jpg\",\"popularity\":4.001149,\"vote_count\":2220,\"video\":false,\"vote_average\":8.13},{\"poster_path\":\"\\/dL11DBPcRhWWnJcFXl9A07MrqTI.jpg\",\"adult\":false,\"overview\":\"Spirited Away is an Oscar winning Japanese animated film about a ten year old girl who wanders away from her parents along a path that leads to a world ruled by strange and unusual monster-like animals. Her parents have been changed into pigs along with others inside a bathhouse full of these creatures. Will she ever see the world how it once was?\",\"release_date\":\"2001-07-20\",\"genre_ids\":[14,12,16,10751],\"id\":129,\"original_title\":\"千と千尋の神隠し\",\"original_language\":\"ja\",\"title\":\"Spirited Away\",\"backdrop_path\":\"\\/g05ZFUUrJM8KsmpsGj8NnF5IyHv.jpg\",\"popularity\":3.404513,\"vote_count\":1718,\"video\":false,\"vote_average\":8.12},{\"poster_path\":\"\\/A2rxR8g3y6kcjIoR2fcwtq9eppc.jpg\",\"adult\":false,\"overview\":\"Dignity. Poise. Mystery. We expect nothing less from the great turn-of-the-century magician, Presto. But when Presto neglects to feed his rabbit one too many times, the magician finds he isn't the only one with a few tricks up his sleeve!\",\"release_date\":\"2008-06-18\",\"genre_ids\":[16,10751],\"id\":13042,\"original_title\":\"Presto\",\"original_language\":\"en\",\"title\":\"Presto\",\"backdrop_path\":\"\\/sFQ10h9DnjOYIF4HjtLQuZ8pnb4.jpg\",\"popularity\":2.428909,\"vote_count\":231,\"video\":false,\"vote_average\":8.1},{\"poster_path\":\"\\/qJ9ouIj4wN24asvKTUSAcJnSfrT.jpg\",\"adult\":false,\"overview\":\"This Oscar-winning animated short film tells the story of one man's love life as seen through the eyes of his best friend and dog, Winston, and revealed bite by bite through the meals they share.\",\"release_date\":\"2014-11-07\",\"genre_ids\":[10749,35,16,18,10751],\"id\":293299,\"original_title\":\"Feast\",\"original_language\":\"en\",\"title\":\"Feast\",\"backdrop_path\":\"\\/fCVPTB6VxfIy2xKGDLt9Ek5OJhm.jpg\",\"popularity\":2.218617,\"vote_count\":273,\"video\":false,\"vote_average\":8.08},{\"poster_path\":\"\\/gzlJkVfWV5VEG5xK25cvFGJgkDz.jpg\",\"adult\":false,\"overview\":\"Ashitaka, a prince of the disappearing Ainu tribe, is cursed by a demonized boar god and must journey to the west to find a cure. Along the way, he encounters San, a young human woman fighting to protect the forest, and Lady Eboshi, who is trying to destroy it. Ashitaka must find a way to bring balance to this conflict.\",\"release_date\":\"1997-07-12\",\"genre_ids\":[12,14,16],\"id\":128,\"original_title\":\"もののけ姫\",\"original_language\":\"ja\",\"title\":\"Princess Mononoke\",\"backdrop_path\":\"\\/dB2rATwfCbsPGfRLIoluBnKdVHb.jpg\",\"popularity\":3.060915,\"vote_count\":829,\"video\":false,\"vote_average\":8.07},{\"poster_path\":\"\\/f7DImXDebOs148U4uPjI61iDvaK.jpg\",\"adult\":false,\"overview\":\"A touching story of an Italian book seller of Jewish ancestry who lives in his own little fairy tale. His creative and happy life would come to an abrupt halt when his entire family is deported to a concentration camp during World War II. While locked up he tries to convince his son that the whole thing is just a game.\",\"release_date\":\"1997-12-20\",\"genre_ids\":[35,18],\"id\":637,\"original_title\":\"La vita è bella\",\"original_language\":\"it\",\"title\":\"Life Is Beautiful\",\"backdrop_path\":\"\\/bORe0eI72D874TMawOOFvqWS6Xe.jpg\",\"popularity\":4.188702,\"vote_count\":1295,\"video\":false,\"vote_average\":8.06},{\"poster_path\":\"\\/bwVhmPpydv8P7mWfrmL3XVw0MV5.jpg\",\"adult\":false,\"overview\":\"In the latter part of World War II, a boy and his sister, orphaned when their mother is killed in the firebombing of Tokyo, are left to survive on their own in what remains of civilian life in Japan. The plot follows this boy and his sister as they do their best to survive in the Japanese countryside, battling hunger, prejudice, and pride in their own quiet, personal battle.\",\"release_date\":\"1988-04-16\",\"genre_ids\":[10752,18,16,10751],\"id\":12477,\"original_title\":\"Hotaru no haka\",\"original_language\":\"ja\",\"title\":\"Grave of the Fireflies\",\"backdrop_path\":\"\\/fCUIuG7y4YKC3hofZ8wsj7zhCpR.jpg\",\"popularity\":1.001651,\"vote_count\":359,\"video\":false,\"vote_average\":8.05},{\"poster_path\":\"\\/tHbMIIF51rguMNSastqoQwR0sBs.jpg\",\"adult\":false,\"overview\":\"The continuing saga of the Corleone crime family tells the story of a young Vito Corleone growing up in Sicily and in 1910s New York; and follows Michael Corleone in the 1950s as he attempts to expand the family business into Las Vegas, Hollywood and Cuba\",\"release_date\":\"1974-12-20\",\"genre_ids\":[18,80],\"id\":240,\"original_title\":\"The Godfather: Part II\",\"original_language\":\"en\",\"title\":\"The Godfather: Part II\",\"backdrop_path\":\"\\/gLbBRyS7MBrmVUNce91Hmx9vzqI.jpg\",\"popularity\":3.332936,\"vote_count\":1676,\"video\":false,\"vote_average\":8.04},{\"poster_path\":\"\\/aAmfIX3TT40zUHGcCKrlOZRKC7u.jpg\",\"adult\":false,\"overview\":\"Growing up can be a bumpy road, and it's no exception for Riley, who is uprooted from her Midwest life when her father starts a new job in San Francisco. Like all of us, Riley is guided by her emotions - Joy, Fear, Anger, Disgust and Sadness. The emotions live in Headquarters, the control center inside Riley's mind, where they help advise her through everyday life. As Riley and her emotions struggle to adjust to a new life in San Francisco, turmoil ensues in Headquarters. Although Joy, Riley's main and most important emotion, tries to keep things positive, the emotions conflict on how best to navigate a new city, house and school.\",\"release_date\":\"2015-06-09\",\"genre_ids\":[35,16,10751],\"id\":150540,\"original_title\":\"Inside Out\",\"original_language\":\"en\",\"title\":\"Inside Out\",\"backdrop_path\":\"\\/szytSpLAyBh3ULei3x663mAv5ZT.jpg\",\"popularity\":7.098415,\"vote_count\":2941,\"video\":false,\"vote_average\":8.04},{\"poster_path\":\"\\/y31QB9kn3XSudA15tV7UWQ9XLuW.jpg\",\"adult\":false,\"overview\":\"Light years from Earth, 26 years after being abducted, Peter Quill finds himself the prime target of a manhunt after discovering an orb wanted by Ronan the Accuser.\",\"release_date\":\"2014-07-30\",\"genre_ids\":[878,14,12],\"id\":118340,\"original_title\":\"Guardians of the Galaxy\",\"original_language\":\"en\",\"title\":\"Guardians of the Galaxy\",\"backdrop_path\":\"\\/bHarw8xrmQeqf3t8HpuMY7zoK4x.jpg\",\"popularity\":10.608508,\"vote_count\":4425,\"video\":false,\"vote_average\":8.02},{\"poster_path\":\"\\/qcL1YfkCxfhsdO6sDDJ0PpzMF9n.jpg\",\"adult\":false,\"overview\":\"The defense and the prosecution have rested and the jury is filing into the jury room to decide if a young Spanish-American is guilty or innocent of murdering his father. What begins as an open and shut case soon becomes a mini-drama of each of the jurors' prejudices and preconceptions about the trial, the accused, and each other.\",\"release_date\":\"1957-03-25\",\"genre_ids\":[18],\"id\":389,\"original_title\":\"12 Angry Men\",\"original_language\":\"en\",\"title\":\"12 Angry Men\",\"backdrop_path\":\"\\/lH2Ga8OzjU1XlxJ73shOlPx6cRw.jpg\",\"popularity\":3.210103,\"vote_count\":957,\"video\":false,\"vote_average\":8},{\"poster_path\":\"\\/rDMxjCYEVnvLC4nsBpB6wjL0LDy.jpg\",\"adult\":false,\"overview\":\"Hana, a nineteen-year-old college student, falls in love with a man only for him to reveal his secret; he is a Wolf Man. Eventually the couple bear two children together; a son and daughter they name Ame and Yuki who both inherit the ability to transform into wolves from their father. When the man Hana fell in love with suddenly dies, she makes the decision to move to a rural town isolated from society to continue raising the children in protection.\",\"release_date\":\"2012-07-21\",\"genre_ids\":[16,14,10751,18],\"id\":110420,\"original_title\":\"おおかみこどもの雨と雪\",\"original_language\":\"ja\",\"title\":\"Wolf Children\",\"backdrop_path\":\"\\/404XqOQeSMmofX1sxMxezmaSjJt.jpg\",\"popularity\":1.944457,\"vote_count\":181,\"video\":false,\"vote_average\":8},{\"poster_path\":\"\\/811DjJTon9gD6hZ8nCjSitaIXFQ.jpg\",\"adult\":false,\"overview\":\"A ticking-time-bomb insomniac and a slippery soap salesman channel primal male aggression into a shocking new form of therapy. Their concept catches on, with underground \\\"fight clubs\\\" forming in every town, until an eccentric gets in the way and ignites an out-of-control spiral toward oblivion.\",\"release_date\":\"1999-10-14\",\"genre_ids\":[18],\"id\":550,\"original_title\":\"Fight Club\",\"original_language\":\"en\",\"title\":\"Fight Club\",\"backdrop_path\":\"\\/8uO0gUM8aNqYLs1OsTBQiXu0fEv.jpg\",\"popularity\":4.586487,\"vote_count\":4646,\"video\":false,\"vote_average\":8},{\"poster_path\":\"\\/1hRoyzDtpgMU7Dz4JF22RANzQO7.jpg\",\"adult\":false,\"overview\":\"Batman raises the stakes in his war on crime. With the help of Lt. Jim Gordon and District Attorney Harvey Dent, Batman sets out to dismantle the remaining criminal organizations that plague the streets. The partnership proves to be effective, but they soon find themselves prey to a reign of chaos unleashed by a rising criminal mastermind known to the terrified citizens of Gotham as the Joker.\",\"release_date\":\"2008-07-16\",\"genre_ids\":[18,28,80,53],\"id\":155,\"original_title\":\"The Dark Knight\",\"original_language\":\"en\",\"title\":\"The Dark Knight\",\"backdrop_path\":\"\\/nnMC0BM6XbjIIrT4miYmMtPGcQV.jpg\",\"popularity\":7.560692,\"vote_count\":7016,\"video\":false,\"vote_average\":8}],\"total_results\":4753,\"total_pages\":238}";
            try
            {
                final URL url = URL;
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                final InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    System.out.println("Buffer Null");
                return null;
                }
                JSON = buffer.toString();

            }catch (IOException e)
            {}
            finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            try {
                return JSon2Data(JSON);
            } catch (final JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
            }

            return null;
        }

        public Movie[] JSon2Data(final String JSON) throws JSONException
        {
            JSONObject jsonObject = new JSONObject(JSON);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            Movie[] movies = new Movie[jsonArray.length()];
            for(int i = 0; i < jsonArray.length();i++)
            {
                JSONObject current = jsonArray.getJSONObject(i);
                movies[i] = new Movie(current.getString("id"),
                        current.getString("original_title"),
                        current.getString("backdrop_path"),
                        current.getString("overview"),
                        current.getString("vote_average"),
                        current.getString("release_date"));
            }
            return movies;
        }


        @Override
        protected void onPostExecute(final Movie[] movieData) {
            super.onPostExecute(movieData);
            Movies = movieData;
            if (movieData != null) {
                posterAdapter.clear();
                for (final Movie movie : movieData) {
                    posterAdapter.add(movie);
                }
                posterAdapter.notifyDataSetChanged();
            }
        }
    }
    }
