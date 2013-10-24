package chessmap

import play.api.Play.current
import play.api.libs.iteratee.{ Enumerator, Concurrent }
import play.api.libs.iteratee.Concurrent.Channel
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.concurrent._

import scala.concurrent.duration._
import akka.actor.Actor
import akka.actor.Props
import akka.event.Logging

case object On
case object Off
case object Push

class StubActor(channel: Channel[String]) extends Actor {

  var isOn = true
  lazy val iterator = Stream.continually(Stub.data.toStream).flatten.toIterator

  def receive = {

    case On ⇒ {
      if (!isOn) {
        isOn = true
        self ! Push
      }

    }

    case Off => {
      isOn = false
    }

    case Push => {
      if (isOn) {
        channel.push(iterator.next)
        Akka.system.scheduler.scheduleOnce(100 milliseconds) {
          self ! Push
        }
      }
    }
  }
}

object Stub {
  val data = List(
    "jpoqcrmg g8h8 200.12.181.3",
    "ygj8b7iv c7c2 109.188.124.63",
    "ygj8b7iv f2f4 91.121.7.111",
    "bb2y20qy d1d7 79.82.34.175",
    "3am5vq6c b7b5 93.130.236.75",
    "tunee74u e5f3 2.185.136.202",
    "w43rdkc6 c7c5 190.39.118.62",
    "kwm4ru4c d1a4 83.203.216.79",
    "3am5vq6c e3d3 95.158.30.92",
    "kwm4ru4c b4d6 91.121.7.111",
    "pqlojt0f c2c3 88.11.35.140",
    "wxzdjepx f6g7 186.54.144.82",
    "ri402woh c8d7 186.120.157.217",
    "f1t7v4a2 d8e7 178.83.62.172",
    "wxzdjepx c1c5 91.121.7.111",
    "z13oklod h8h7 86.57.56.75",
    "foge48a4 e6d6 85.26.184.89",
    "ri402woh f3e5 91.121.7.111",
    "ot17yc30 f5f6 193.126.151.142",
    "3u7nbw0p g5g6 151.51.96.68",
    "7nfkodrg c5c6 75.164.205.194",
    "f1t7v4a2 c1g5 91.121.7.111",
    "foge48a4 d8c8 91.121.7.111",
    "3u7nbw0p c6f3 91.121.7.111",
    "ertua3bf e7e6 188.242.207.49",
    "zbfx1kfh f6d5 81.190.228.255",
    "9p16tle4 h2h3 176.92.103.60",
    "nxg9epco d8d7 186.47.119.189",
    "mov3okb8 f8f7 88.119.48.85",
    "mov3okb8 c4d5 91.121.7.111",
    "77pg8u0z d3e3 109.8.122.158",
    "4vgg82by c7c6 82.66.151.94",
    "4vgg82by g1f3 91.121.7.111",
    "7ef3cgv9 g1f3 187.74.84.127",
    "dd9cu8j1 c1b1 78.179.53.237",
    "f8ak22ly g6h6 151.42.62.182",
    "f8ak22ly b4b3 91.121.7.111",
    "9vswaelp g2g4 91.133.152.184",
    "xrnbxmq1 c7c6 66.173.82.30",
    "uffxiw8m h2h1 85.65.208.107",
    "hwaei2go f7f6 88.172.240.18",
    "fn9kxjkb b2b3 46.193.130.250",
    "fn9kxjkb c8f5 91.121.7.111",
    "cvyy5nlh b4b5 187.170.110.121",
    "y3xg5p1d d6f4 95.10.133.226",
    "m82djgec b2b8 187.192.188.170",
    "goy9x1xj c7c5 89.142.149.91",
    "ad3m6k55 f2g3 176.14.243.186",
    "ad3m6k55 a8b8 91.121.7.111",
    "h4j1s2ip d2d4 37.215.179.100",
    "lp0hmt38 f5g4 77.52.14.72",
    "lp0hmt38 b1c3 91.121.7.111",
    "l5y1akt6 a2a3 178.159.218.97",
    "hipj6j8o c2d3 92.108.129.145",
    "istoipuw g3e3 199.17.159.161",
    "eg8oc2bv f1d3 129.105.81.217",
    "q1dson3a b7b5 193.190.253.150",
    "vj0lyamz g2h3 83.112.174.197",
    "jqkr5xpg g2h2 178.148.152.226",
    "eiaz2aws c8b7 123.100.83.24",
    "0jgx7tn7 h3h7 31.33.93.117",
    "b4vy91v9 e1e3 46.72.89.56",
    "2bgjdv23 g2g3 190.248.184.92",
    "2bgjdv23 f5g4 91.121.7.111",
    "i5caz6ds f3g5 201.210.66.198",
    "2jn6mely g3f4 86.131.192.116",
    "h060efbc d7d6 151.247.47.33",
    "vcj8nftd e2b5 176.117.140.75",
    "ertua3bf d2d4 134.76.186.44",
    "3qopxoto b8c6 199.216.46.203",
    "q2xy502h a7a6 178.180.206.250",
    "yo73m2eq h7h6 195.78.96.203",
    "8cft3wbl e2e4 187.163.59.89",
    "ayt7fl9z c2c3 134.93.74.231",
    "m6lsksib e2e4 41.103.30.66",
    "5ukah68o b5c7 62.221.107.251",
    "duznugx3 e4d5 89.165.196.226",
    "mjytxwyb e3d3 82.241.48.128",
    "el73ot0m c5g1 188.134.46.145",
    "out9ldtt c1b2 86.195.48.36",
    "6f8achpj b8c6 24.254.189.248",
    "uffxiw8m h4g3 46.185.16.75",
    "h741vlsg d2d3 189.199.161.205",
    "5d6kwvtz e4e5 81.36.166.50",
    "750as1te f2g2 93.87.213.21",
    "0ogz1ke0 d3a3 88.1.77.53",
    "2au228pq g5f6 80.76.106.112",
    "dds04tvs d6c5 93.86.152.152",
    "4vgg82by d8c7 82.66.151.94",
    "2au228pq b6b2 91.121.7.111",
    "4vgg82by e2e4 91.121.7.111",
    "ertua3bf b8c6 188.242.207.49",
    "botkf4cq d1a4 83.149.37.251",
    "hipj6j8o g1f2 95.54.190.137",
    "llpe6zpf d1d7 46.115.106.106",
    "xrnbxmq1 b1c3 2.158.41.218",
    "7ef3cgv9 c8g4 80.245.229.204",
    "mbpmgxeo e2e4 95.139.71.97",
    "dds04tvs b5d4 213.213.247.72",
    "5jufrxih c6c5 78.62.202.179",
    "otfb2rcm f6g6 88.180.184.126",
    "q1dson3a d3c2 188.13.154.219",
    "eg8oc2bv f8g7 109.93.159.58",
    "esxec44n e7e5 81.111.79.215",
    "p7jv4h75 f8d6 41.224.125.22",
    "ummgug1t e6e7 201.245.52.196",
    "ummgug1t d1d6 91.121.7.111",
    "nxg9epco f3d4 179.219.121.59",
    "7p5twiof h8f8 46.70.34.243",
    "7p5twiof f3e2 91.121.7.111",
    "dcth2tif f8d6 83.33.155.14",
    "bfkvqybp d4d5 178.38.90.16",
    "dcth2tif e2e3 91.121.7.111",
    "4rtd443e d1e2 212.200.65.107",
    "g70roq12 c5e4 77.46.214.30",
    "1st9lrkt a5a6 78.234.21.102",
    "bb2y20qy e7e8 2.106.140.255",
    "ykj5h87p f1f2 2.5.210.235",
    "a5gf0lx1 g6f7 46.216.26.143",
    "pqlojt0f b8c6 5.149.151.215",
    "7rgnj7ld d1b3 129.42.208.176",
    "xpbtx56s c1g5 181.74.152.60",
    "9p16tle4 g4h5 14.96.117.238",
    "xpbtx56s d5c5 91.121.7.111",
    "2llg0k75 g8e6 78.185.144.75",
    "5wquf0ec g3g2 46.49.83.8",
    "dxy3s0af e5e1 94.173.119.144",
    "4xq4yst6 d7e6 41.230.217.83",
    "5wquf0ec f2f3 91.121.7.111",
    "3a4l97bw e7e5 198.50.215.220",
    "u6o8sg0z e2e4 186.29.121.15",
    "u6o8sg0z e7e5 91.121.7.111",
    "2yyl1tlj h4h2 79.180.51.67",
    "zreh33ud b7b6 79.143.104.126",
    "ibuj8l9e e5f7 89.101.63.206",
    "r6kjrtum c6b4 77.28.219.35",
    "6gppsqh8 d7b6 85.97.135.89",
    "b0rqky4q f1c1 68.186.18.175",
    "b0rqky4q c8d7 91.121.7.111",
    "2rac38b1 h7h6 93.86.65.184",
    "bremmsqf h2h3 212.62.63.85",
    "hipj6j8o g7d7 92.108.129.145",
    "vj0lyamz g3g4 91.124.66.49",
    "w43rdkc6 d4d5 213.87.128.88",
    "reo5bmh0 c5c4 79.156.7.91",
    "reo5bmh0 e7e6 91.121.7.111",
    "19z754lr c1c2 31.146.76.159",
    "dds04tvs h2h3 93.86.152.152",
    "5mrn7c14 g5f5 83.23.221.16",
    "19z754lr f7f5 91.121.7.111",
    "5mrn7c14 b4e7 91.121.7.111",
    "rs9dqx9f g4g7 188.73.252.149",
    "k3sa5r1c a2a3 178.222.89.116",
    "pqlojt0f d2d4 88.11.35.140",
    "4vgg82by e7e5 82.66.151.94",
    "i5gi6jbc d8e8 85.106.5.192",
    "4vgg82by c1d2 91.121.7.111",
    "idoh517z c3d4 81.251.205.138",
    "zsaclrkp f8f7 212.242.126.74",
    "ertua3bf f2f4 134.76.186.44",
    "hbygvijs e4h7 188.131.0.71",
    "vlpducud g2e3 176.57.56.76",
    "7fv8z9tt b4c3 95.26.217.125",
    "q1dson3a c5c4 193.190.253.150",
    "bb2y20qy f1d1 79.82.34.175",
    "2ktj3d88 b2b3 24.46.60.39",
    "k34mockd a1d1 83.43.150.18",
    "mlvkcrv5 g4f3 5.43.100.231",
    "mlvkcrv5 e8e7 91.121.7.111",
    "zbfx1kfh d1h5 79.172.91.45",
    "cey7mn81 b7d5 88.1.84.240",
    "n0vbipme e2g1 78.31.230.69",
    "n0vbipme c7d8 91.121.7.111",
    "eg8oc2bv e1g1 129.105.81.217",
    "dds04tvs d4f3 213.213.247.72",
    "p40emi0g c3e1 109.101.246.151",
    "5jufrxih e3e4 2.95.64.43",
    "fn9kxjkb b1c3 46.193.130.250",
    "p40emi0g c5d5 91.121.7.111",
    "fn9kxjkb b8d7 91.121.7.111",
    "ayt7fl9z c7c5 79.173.80.69",
    "tunee74u e6e5 181.64.80.224",
    "goy9x1xj b4c5 95.69.140.249",
    "8o58oyv9 e7d7 187.101.39.173",
    "tmznlwew d1d2 88.244.136.38",
    "a57hctdm a4c3 178.20.79.82",
    "2bgjdv23 f1g2 190.248.184.92",
    "a57hctdm e8g8 91.121.7.111",
    "2bgjdv23 f8e8 91.121.7.111",
    "6dvp3347 b1c3 83.89.37.243",
    "kwm4ru4c b1c3 83.203.216.79",
    "kwm4ru4c a7a6 91.121.7.111",
    "86apmnt8 d2d4 178.36.135.183",
    "86apmnt8 e5d4 91.121.7.111",
    "ertua3bf b7b6 188.242.207.49",
    "zreh33ud g1f3 31.40.36.215",
    "1ydj8uxa f1d3 193.198.35.33",
    "f8ak22ly h6h5 151.42.62.182",
    "zhg4ron7 b5c4 189.89.67.207",
    "q2xy502h b5a4 176.125.214.226",
    "l6m4w2ou d3d4 177.106.161.146",
    "nxg9epco e5d4 186.47.119.189",
    "f8ak22ly e5d4 91.121.7.111",
    "s390k58p d4f3 94.43.70.96",
    "25r6nol3 e1d2 88.70.146.158",
    "mqt8y0dh f4f5 188.129.114.203"
  )

  val enumerator = Enumerator(
    "jpoqcrmg g8h8 200.12.181.3",
    "ygj8b7iv c7c2 109.188.124.63",
    "ygj8b7iv f2f4 91.121.7.111",
    "bb2y20qy d1d7 79.82.34.175",
    "3am5vq6c b7b5 93.130.236.75",
    "tunee74u e5f3 2.185.136.202",
    "w43rdkc6 c7c5 190.39.118.62",
    "kwm4ru4c d1a4 83.203.216.79",
    "3am5vq6c e3d3 95.158.30.92",
    "kwm4ru4c b4d6 91.121.7.111",
    "pqlojt0f c2c3 88.11.35.140",
    "wxzdjepx f6g7 186.54.144.82",
    "ri402woh c8d7 186.120.157.217",
    "f1t7v4a2 d8e7 178.83.62.172",
    "wxzdjepx c1c5 91.121.7.111",
    "z13oklod h8h7 86.57.56.75",
    "foge48a4 e6d6 85.26.184.89",
    "ri402woh f3e5 91.121.7.111",
    "ot17yc30 f5f6 193.126.151.142",
    "3u7nbw0p g5g6 151.51.96.68",
    "7nfkodrg c5c6 75.164.205.194",
    "f1t7v4a2 c1g5 91.121.7.111",
    "foge48a4 d8c8 91.121.7.111",
    "3u7nbw0p c6f3 91.121.7.111",
    "ertua3bf e7e6 188.242.207.49"
  )
}


