<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> 
<html class="no-js"> <!--<![endif]-->
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta name="description" content="">
  
  <meta name="author" content="">

  <title>言承旭</title>
<!-- Mobile Specific Meta
  ================================================== -->
  <meta name="viewport" content="width=device-width, initial-scale=1">
  
  <!-- Favicon -->
  <link rel="shortcut icon" type="image/x-icon" href="img/favicon.png" />
  
  <!-- CSS
  ================================================== -->
  <!-- RS5.0 Main Stylesheet -->
  <link rel="stylesheet" type="text/css" href="revolution/css/settings.css">
  <!-- RS5.0 Layers and Navigation Styles -->
  <link rel="stylesheet" type="text/css" href="revolution/css/layers.css">
  <link rel="stylesheet" type="text/css" href="revolution/css/navigation.css">
  <!-- REVOLUTION STYLE SHEETS -->
  <link rel="stylesheet" type="text/css" href="revolution/fonts/pe-icon-7-stroke/css/pe-icon-7-stroke.css">
  <link rel="stylesheet" type="text/css" href="revolution/fonts/font-awesome/css/font-awesome.min.css">
  <link rel="stylesheet" type="text/css" href="revolution/css/settings.css">
  <link rel="stylesheet" type="text/css" href="revolution/css/layers.css">
  <link rel="stylesheet" type="text/css" href="revolution/css/navigation.css"> 
  <!-- Themefisher Icon font -->
  <link rel="stylesheet" href="css/vendor/tf-fonts.min.css">
  <!-- bootstrap.min css -->
  <link rel="stylesheet" href="css/vendor/bootstrap.min.css">
  <!-- Lightbox.min css -->
  <link rel="stylesheet" href="css/vendor/lightbox.min.css">
  <!-- Animate.css -->
  <link rel="stylesheet" href="css/vendor/animate.min.css">
  <!-- Owl Carousel -->
  <link rel="stylesheet" href="css/vendor/owl.carousel.css">
  <!-- Main Stylesheet -->
  <link rel="stylesheet" href="css/style.css">
  <!-- Media Queries -->
  <link rel="stylesheet" href="css/vendor/media-queries.css">


  <!-- Colors -->
  <link rel="stylesheet" type="text/css" href="css/colors/green.css" title="green">
  <link rel="stylesheet" type="text/css" href="css/colors/red.css" title="light-red">
  <link rel="stylesheet" type="text/css" href="css/colors/blue.css" title="blue">
  <link rel="stylesheet" type="text/css" href="css/colors/light-blue.css" title="light-blue">
  <link rel="stylesheet" type="text/css" href="css/colors/yellow.css" title="yellow">
  <link rel="stylesheet" type="text/css" href="css/colors/light-green.css" title="light-green">
  
  <script src="js/vendor/modernizr.js"></script>

  <script src="https://img.hcharts.cn/jquery/jquery-1.8.3.min.js"></script>
        <script src="https://img.hcharts.cn/highstock/highstock.js"></script>
        <script src="https://img.hcharts.cn/highcharts/modules/exporting.js"></script>
        <script src="https://img.hcharts.cn/highcharts-plugins/highcharts-zh_CN.js"></script>

        <script src="https://img.hcharts.cn/highcharts/highcharts.js"></script>
        <script src="https://img.hcharts.cn/highcharts/highcharts-more.js"></script>
        <script src="https://img.hcharts.cn/highcharts/modules/data.js"></script>
<style>
.nav>li {
    float: left;
}
</style>
</head>


<body id="body">

 <!--
  Start Preloader
  ==================================== -->
  <div id="preloader">
    <div class='preloader'>
      <span>L</span>
      <span>A</span>
      <span>I</span>
      <span>T</span>
      <span>O</span>
      <span>U</span>
    </div>
  </div> 
  <!--
  End Preloader
  ==================================== -->

  

<!-- 
  Fixed Navigation
  ==================================== -->
  <header class="navigation navbar navbar-fixed-top sticky-header" style="padding-bottom: 0;">
    <div class="container">
      <div class="navbar-header">
        <!-- responsive nav button -->
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
          <span class="sr-only">Toggle navigation</span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
        </button>
        <!-- /responsive nav button -->

        <!-- logo -->
        <a class="navbar-brand logo" href="index.html">
          <img src="images/logo.png" alt="Meghna" />
        </a>
        <!-- /logo -->
      </div>

      <!-- main nav -->
		  <nav class="navbar-collapse navbar-right collapse" aria-expanded="false" style="border-top: 1px solid; border-bottom: 1px solid; height: 2px;">
			<ul id="nav" class="nav navbar-nav menu">
			  <li class="current"><a href="index.html">首页</a></li>
			  <li><a href="about.html">神经质</a></li>
			  <li><a href="service.html">有趣无用</a></li>
			  <li><a href="portfolio.html">深深的世界</a></li>
			  <li><a href="team.html">我美美</a></li>
			  <li><a href="pricing.html">我有话说，憋不住了</a></li>
			  <li><a href="contact.html">好啊好啊</a></li>
			</ul>
		  </nav>
      <!-- /main nav -->

    </div>
  </header>
  <!--
  End Fixed Navigation
  ==================================== -->
		<section class="team-skills parallax-section" id="skills" style="padding-top: 100px;padding-bottom: 10px;">
			<div class="container">
				<div class="row" id="row">
					<div class="col-md-5">
						<div class="team-skills-content">
							<h3>${vote.title}</h3>
							<#list vote.voteOptionMini as voteOption>
							<li>
                                <span>${voteOption.option}</span>
                                <div class="progress">
                                    <div class="progress-bar" style="width: 90%;">
                                    </div>
                                </div>
                            </li>
                            </#list>
						</div>
					</div>
					 <!--<div class="col-md-6 col-md-offset-1">
						<div class="progress-block">
							<ul>
							    <#list vote.voteOptionMini as voteOption>
								<li>
                                    <span>${voteOption.option}</span>
                                    <div class="progress">
                                        <div class="progress-bar" style="width: 90%;">
                                        </div>
                                    </div>
                                </li>
                                </#list>
							</ul>
						</div>
					</div>-->
				</div>
				<div class="row">
					<div class="col-md-12">
						<div id="clients-slider" class="clients-logo-slider">
                            <div id="chart-stock" ></div>
                            <div id="chart-map" ></div>
                            <div id="chart-stack"></div>
						</div>
					</div>
				</div>
			</div>   	<!-- End container -->
		</section>   <!-- End section -->

        <div style="display:none">
                <!-- Source: http://or.water.usgs.gov/cgi-bin/grapher/graph_windrose.pl -->
                <table id="freq" border="0" cellspacing="0" cellpadding="0">
                    <tr nowrap bgcolor="#CCCCFF">
                        <th colspan="9" class="hdr">Table of Frequencies (percent)</th>
                    </tr>
                    <tr nowrap bgcolor="#CCCCFF">
                        <th class="freq">Direction</th>
                        <th class="freq">< 0.5 m/s</th>
                        <th class="freq">0.5-2 m/s</th>
                        <th class="freq">2-4 m/s</th>
                        <th class="freq">4-6 m/s</th>
                        <th class="freq">6-8 m/s</th>
                        <th class="freq">8-10 m/s</th>
                        <th class="freq">> 10 m/s</th>
                        <th class="freq">Total</th>
                    </tr>
                    <tr nowrap>
                        <td class="dir">N</td>
                        <td class="data">1.81</td>
                        <td class="data">1.78</td>
                        <td class="data">0.16</td>
                        <td class="data">0.00</td>
                        <td class="data">0.00</td>
                        <td class="data">0.00</td>
                        <td class="data">0.00</td>
                        <td class="data">3.75</td>
                    </tr>		
                    <tr nowrap bgcolor="#DDDDDD">
                        <td class="dir">NNE</td>
                        <td class="data">0.62</td>
                        <td class="data">1.09</td>
                        <td class="data">0.00</td>
                        <td class="data">0.00</td>
                        <td class="data">0.00</td>
                        <td class="data">0.00</td>
                        <td class="data">0.00</td>
                        <td class="data">1.71</td>
                    </tr>
                    <tr nowrap>
                        <td class="dir">NE</td>
                        <td class="data">0.82</td>
                        <td class="data">0.82</td>
                        <td class="data">0.07</td>
                        <td class="data">0.00</td>
                        <td class="data">0.00</td>
                        <td class="data">0.00</td>
                        <td class="data">0.00</td>
                        <td class="data">1.71</td>
                    </tr>
                    <tr nowrap bgcolor="#DDDDDD">
                        <td class="dir">ENE</td>
                        <td class="data">0.59</td>
                        <td class="data">1.22</td>
                        <td class="data">0.07</td>
                        <td class="data">0.00</td>
                        <td class="data">0.00</td>
                        <td class="data">0.00</td>
                        <td class="data">0.00</td>
                        <td class="data">1.88</td>
                    </tr>
                    <tr nowrap>
                        <td class="dir">E</td>
                        <td class="data">0.62</td>
                        <td class="data">2.20</td>
                        <td class="data">0.49</td>
                        <td class="data">0.00</td>
                        <td class="data">0.00</td>
                        <td class="data">0.00</td>
                        <td class="data">0.00</td>
                        <td class="data">3.32</td>
                    </tr>
                    <tr nowrap bgcolor="#DDDDDD">
                        <td class="dir">ESE</td>
                        <td class="data">1.22</td>
                        <td class="data">2.01</td>
                        <td class="data">1.55</td>
                        <td class="data">0.30</td>
                        <td class="data">0.13</td>
                        <td class="data">0.00</td>
                        <td class="data">0.00</td>
                        <td class="data">5.20</td>
                    </tr>
                    <tr nowrap>
                        <td class="dir">SE</td>
                        <td class="data">1.61</td>
                        <td class="data">3.06</td>
                        <td class="data">2.37</td>
                        <td class="data">2.14</td>
                        <td class="data">1.74</td>
                        <td class="data">0.39</td>
                        <td class="data">0.13</td>
                        <td class="data">11.45</td>
                    </tr>
                    <tr nowrap bgcolor="#DDDDDD">
                        <td class="dir">SSE</td>
                        <td class="data">2.04</td>
                        <td class="data">3.42</td>
                        <td class="data">1.97</td>
                        <td class="data">0.86</td>
                        <td class="data">0.53</td>
                        <td class="data">0.49</td>
                        <td class="data">0.00</td>
                        <td class="data">9.31</td>
                    </tr>
                    <tr nowrap>
                        <td class="dir">S</td>
                        <td class="data">2.66</td>
                        <td class="data">4.74</td>
                        <td class="data">0.43</td>
                        <td class="data">0.00</td>
                        <td class="data">0.00</td>
                        <td class="data">0.00</td>
                        <td class="data">0.00</td>
                        <td class="data">7.83</td>
                    </tr>
                    <tr nowrap bgcolor="#DDDDDD">
                        <td class="dir">SSW</td>
                        <td class="data">2.96</td>
                        <td class="data">4.14</td>
                        <td class="data">0.26</td>
                        <td class="data">0.00</td>
                        <td class="data">0.00</td>
                        <td class="data">0.00</td>
                        <td class="data">0.00</td>
                        <td class="data">7.37</td>
                    </tr>
                    <tr nowrap>
                        <td class="dir">SW</td>
                        <td class="data">2.53</td>
                        <td class="data">4.01</td>
                        <td class="data">1.22</td>
                        <td class="data">0.49</td>
                        <td class="data">0.13</td>
                        <td class="data">0.00</td>
                        <td class="data">0.00</td>
                        <td class="data">8.39</td>
                    </tr>
                    <tr nowrap bgcolor="#DDDDDD">
                        <td class="dir">WSW</td>
                        <td class="data">1.97</td>
                        <td class="data">2.66</td>
                        <td class="data">1.97</td>
                        <td class="data">0.79</td>
                        <td class="data">0.30</td>
                        <td class="data">0.00</td>
                        <td class="data">0.00</td>
                        <td class="data">7.70</td>
                    </tr>
                    <tr nowrap>
                        <td class="dir">W</td>
                        <td class="data">1.64</td>
                        <td class="data">1.71</td>
                        <td class="data">0.92</td>
                        <td class="data">1.45</td>
                        <td class="data">0.26</td>
                        <td class="data">0.10</td>
                        <td class="data">0.00</td>
                        <td class="data">6.09</td>
                    </tr>
                    <tr nowrap bgcolor="#DDDDDD">
                        <td class="dir">WNW</td>
                        <td class="data">1.32</td>
                        <td class="data">2.40</td>
                        <td class="data">0.99</td>
                        <td class="data">1.61</td>
                        <td class="data">0.33</td>
                        <td class="data">0.00</td>
                        <td class="data">0.00</td>
                        <td class="data">6.64</td>
                    </tr>
                    <tr nowrap>
                        <td class="dir">NW</td>
                        <td class="data">1.58</td>
                        <td class="data">4.28</td>
                        <td class="data">1.28</td>
                        <td class="data">0.76</td>
                        <td class="data">0.66</td>
                        <td class="data">0.69</td>
                        <td class="data">0.03</td>
                        <td class="data">9.28</td>
                    </tr>		
                    <tr nowrap bgcolor="#DDDDDD">
                        <td class="dir">NNW</td>
                        <td class="data">1.51</td>
                        <td class="data">5.00</td>
                        <td class="data">1.32</td>
                        <td class="data">0.13</td>
                        <td class="data">0.23</td>
                        <td class="data">0.13</td>
                        <td class="data">0.07</td>
                        <td class="data">8.39</td>
                    </tr>
                    <tr nowrap>
                        <td class="totals">Total</td>
                        <td class="totals">25.53</td>
                        <td class="totals">44.54</td>
                        <td class="totals">15.07</td>
                        <td class="totals">8.52</td>
                        <td class="totals">4.31</td>
                        <td class="totals">1.81</td>
                        <td class="totals">0.23</td>
                        <td class="totals"> </td>
                    </tr>
                </table>
            </div>
        <footer id="footer" class="bg-one">
            <!-- end container -->
           </div>
           <div class="footer-bottom" style="background-color: white;border-top: 1px solid #dcc7c7;">
             <h5><a>处子网 版权所有</a></h5>
           </div>
      
     </footer> <!-- end footer -->


    <!-- end Footer Area
    ========================================== -->
    
    <!-- 
    Essential Scripts
    =====================================-->
    
    <!-- Main jQuery -->
    <script src="js/vendor/jquery-3.1.1.js"></script>
    <!-- Bootstrap 3.1 -->
    <script src="js/vendor/bootstrap.min.js"></script>
    <!-- Parallax -->
    <script src="js/vendor/jquery.parallax-1.1.3.js"></script>
    <!-- lightbox -->
    <script src="js/vendor/lightbox.min.js"></script>
    <!-- Owl Carousel -->
    <script src="js/vendor/owl.carousel.min.js"></script>
    <!-- Portfolio Filtering -->
    <script src="js/vendor/jquery.mixitup.js"></script>
    <!-- Smooth Scroll js -->
    <script src="js/vendor/smooth-scroll.min.js"></script>
    
    <script src="js/vendor/jquery.vide.min.js"></script>
    <!-- Custom js -->
    <script src="js/script.js"></script>
    <script src="js/chart-stock.js"></script>
  </body>
  </html>

