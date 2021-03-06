package com.anroid.bottommenu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity2 : AppCompatActivity() {

    init{
        instance = this
    }

    companion object{
        private var instance:MainActivity2? = null
        fun getInstance(): MainActivity2? {
            return instance
        }
    }

    private val fl: FrameLayout by lazy {
        findViewById(R.id.fl_)
    }

    private val bn: BottomNavigationView by lazy {
        findViewById(R.id.bn_)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        supportFragmentManager.beginTransaction().add(fl.id, HomeFragment()).commit()

        bn.setOnNavigationItemSelectedListener {
            replaceFragment(
                when (it.itemId) {
                    R.id.menu_home -> HomeFragment()
                    R.id.menu_review -> ReviewFragment()
                    else -> MypageFragment()
                }
            )
            true
        }
    }

    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(fl.id, fragment).commit()
    }

    fun reviewToMypage() {
        replaceFragment(MypageFragment())
        bn.menu.getItem(2).isChecked = true
    }

    fun mypageToReview(fragment: Fragment, reviewList: Review) {
        val bundle = Bundle()
        bundle.putInt("alias", reviewList.alias)
        bundle.putString("title", reviewList.title)
        bundle.putString("genre", reviewList.genre)
        bundle.putString("category", reviewList.category)
        bundle.putString("reviewContent", reviewList.review)
        bundle.putString("description", reviewList.description)
        bundle.putFloat("rating", reviewList.rating)
        bundle.putString("emotion", reviewList.emotion)
        bundle.putString("recommend", reviewList.recommend)
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction().replace(R.id.fl_, fragment).commit()
        bn.menu.getItem(1).isChecked = true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId) {
            R.id.report -> {
                val email = Intent(Intent.ACTION_SEND)
                email.type = "plain/text"
                val address = arrayOf("yilllios.03@gmail.com")
                email.putExtra(Intent.EXTRA_EMAIL, address)
                email.putExtra(Intent.EXTRA_SUBJECT, "???????????????.")
                email.putExtra(Intent.EXTRA_TEXT, "????????? ????????? ????????? ?????????")
                startActivity(email)
                return true
            }
            R.id.logout -> {
                val dlg = AlertDialog.Builder(this)
                dlg.setMessage("???????????? ???????????????????")
                dlg.setPositiveButton("??????") { dialog, which ->
                    val intent= Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "???????????????", Toast.LENGTH_SHORT).show()
                }
                dlg.setNegativeButton("??????") {dialog, which ->
                    Toast.makeText(this, "?????????", Toast.LENGTH_SHORT).show()
                }
                dlg.show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
