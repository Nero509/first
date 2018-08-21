package com.example.nero.sensors.couchbase

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.couchbase.lite.*
import com.example.nero.sensors.R
import java.net.URI


class DatabaseLite : AppCompatActivity() {

    private lateinit var database:Database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_database)

        val config = DatabaseConfiguration(applicationContext)
        database = Database("mydb", config)


        save()

        get()

        val dt=database.getDocument("demo")

        Log.i("COUCHLITE",dt.getString("type"))



        val dt2=database.getDocument("demo").toMutable()
        dt2.setString("type","new")
        database.save(dt2)
        val dtx=database.getDocument("demo")
        Log.i("COUCHLITE",dtx.getString("type"))


        /*val repl=URLEndpoint(URI("ws://demos.kaazing.com/echo"))
        val fg=ReplicatorConfiguration(database,repl)
        fg.replicatorType=ReplicatorConfiguration.ReplicatorType.PUSH_AND_PULL
        val reeee=Replicator(fg)
        reeee.addChangeListener {
            Toast.makeText(this@DatabaseLite,"ghhfh",Toast.LENGTH_LONG).show()
            if (it.getStatus().getError() != null)
                Log.i("ghfhfh", "Error code ::  " + it.getStatus().getError().getCode());
        }
        reeee.start()*/

    }

    private fun save(){
        // Create a new document (i.e. a record) in the database.
        val mutableDoc = MutableDocument("demo")
                .setFloat("version", 2.0f)
                .setString("type", "abs")
        // Save it to the database.
        if (this::database.isInitialized) {
            database.save(mutableDoc)
        } else {
            database = Database("mydb", DatabaseConfiguration(applicationContext))
            database.save(mutableDoc)
        }
    }

    private fun get(){
        /*val qry=QueryBuilder.select(SelectResult.all()).from(DataSource.database(database)).
                where(Expression.property("type").equalTo(Expression.string("SDK")))*/


        val qry=QueryBuilder.select(SelectResult.expression(Meta.id),
                SelectResult.property("version"),
                SelectResult.property("type")).from(DataSource.database(database)).
                where(Expression.property("type").equalTo(Expression.string("abs")))

        val res=qry.execute()
        Log.i("COUCHLITE", "Number of rows ::  " + res.allResults().size)
        for(dt in res){
            Log.i("COUCHLITE", String.format("version", dt.getString("version")))
        }


        /*for (result in res) {
            Log.i("COUCHLITE", String.format("type->", result.getFloat("type")))
            Log.i("COUCHLITE", String.format("version", result.getString("version")))
        }*/


    }


}
