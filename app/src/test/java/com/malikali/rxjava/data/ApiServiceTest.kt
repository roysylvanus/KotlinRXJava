package com.malikali.rxjava.data

import com.malikali.rxjava.models.Animal
import com.malikali.rxjava.utils.Constants
import com.malikali.rxjava.utils.ZoneDateTimeProvider
import com.malikali.rxjava.utils.ZoneDateTimeProvider.loadTimeZone
import io.reactivex.observers.TestObserver
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.Charset

@RunWith(JUnit4::class)
class ApiServiceTest {

    private lateinit var service: ApiService
    private lateinit var mockWebServer: MockWebServer


    @Before
    fun init(){
        ZoneDateTimeProvider.loadTimeZone()
        mockWebServer = MockWebServer()

        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

    }

    @Test
    fun getRandomAnimal(){

        enqueueResponse("random-animal-response.json")
        val  testObserver = TestObserver<Animal>()

        service.getRandomAnimal().subscribe(testObserver)

        testObserver.await()
            .assertValue {
                return@assertValue it.name == "Siamang"
            }
            .assertComplete()
            .assertNoErrors()

        val takeRequest = mockWebServer.takeRequest()
        assertThat(takeRequest.path,`is`(Constants.RANDOM_ANIMAL))
    }




    @After
    fun cleanUp(){
        mockWebServer.shutdown()
    }

    private fun enqueueResponse(fileName:String,headers : Map<String,String> = emptyMap()){

        val inputStream = javaClass.classLoader!!.getResourceAsStream("api-response/$fileName")
        val source =  inputStream.source().buffer()
        val mockResponse = MockResponse()

        for ((key,value ) in headers){
            mockResponse.addHeader(key,value)
        }

        mockWebServer.enqueue(mockResponse.setBody(source.readString(Charsets.UTF_8)))
    }
}