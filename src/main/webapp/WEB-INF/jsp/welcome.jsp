<%--
  Created by IntelliJ IDEA.
  User: savidude
  Date: 8/29/18
  Time: 7:26 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en-us">

<head>
    <title>ORFanGenes</title>
    <meta charset="UTF-8">
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

    <link rel="shortcut icon" type="image/x-icon" href="assets/favicon.ico" />

    <link href="https://fonts.googleapis.com/css?family=Roboto:400,100,100italic,300,300italic,400italic,500,500italic,700italic,700,900,900italic" rel="stylesheet">

    <!-- STYLESHEETS -->
    <style type="text/css">
        [fuse-cloak],
        .fuse-cloak {
            display: none !important;
        }
    </style>

    <!-- Icons.css -->
    <link type="text/css" rel="stylesheet" href="assets/bower_components/mdi/css/materialdesignicons.min.css">
    <!-- Fuse Html -->
    <link type="text/css" rel="stylesheet" href="assets/fuse-html/fuse-html.min.css" />
    <!-- Main CSS -->
    <link type="text/css" rel="stylesheet" href="assets/css/main.css">
    <!-- Home CSS -->
    <link type="text/css" rel="stylesheet" href="assets/css/style.css">
    <!-- / STYLESHEETS -->

    <!-- JAVASCRIPT -->
    <!-- jQuery -->
    <script type="text/javascript" src="assets/bower_components/jquery/dist/jquery.min.js"></script>
    <!-- Mobile Detect -->
    <script type="text/javascript" src="assets/bower_components/mobile-detect/mobile-detect.min.js"></script>
    <!-- Bootstrap -->
    <script type="text/javascript" src="assets/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
    <!-- Fuse Html -->
    <script type="text/javascript" src="assets/fuse-html/fuse-html.min.js"></script>
    <!-- Main JS -->
    <script type="text/javascript" src="assets/js/main.js"></script>
    <!-- / JAVASCRIPT -->
</head>

<body class="layout layout-vertical layout-left-navigation layout-above-toolbar layout-above-footer">
<main>
    <div id="wrapper">
        <aside id="aside" class="aside aside-left" data-fuse-bar="aside" data-fuse-bar-media-step="md" data-fuse-bar-position="left">
            <div class="aside-content bg-primary-700 text-auto">

                <div class="aside-toolbar">

                    <div class="logo">
                        <span class="logo-icon">OF</span>
                        <span class="logo-text">ORFanGenes</span>
                    </div>

                    <button id="toggle-fold-aside-button" type="button" class="btn btn-icon d-none d-lg-block" data-fuse-aside-toggle-fold>
                        <i class="icon icon-backburger"></i>
                    </button>

                </div>

                <ul class="nav flex-column" id="sidenav" data-children=".nav-item">

                    <li class="nav-item">
                        <a class="nav-link ripple active" href="/" data-url="/">
                            <i class="mdi s-4 mdi-home"></i>
                            <span>Home</span>
                        </a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link ripple" href="/input" data-url="/">
                            <i class="mdi s-4 mdi-cursor-pointer"></i>
                            <span>Input</span>
                        </a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link ripple" href="https://docs.google.com/document/d/1VAhvAmmU4mQh-D93MVw4TfsgJZlo3VqIto1sOw8oOt4/edit?usp=sharing" data-url="/">
                            <i class="mdi s-4 mdi-library-books"></i>
                            <span>Instructions</span>
                        </a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link ripple" href="/results" data-url="/">
                            <i class="mdi s-4 mdi-clipboard"></i>
                            <span>Results</span>
                        </a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link ripple" href="/orfanbase" data-url="/">
                            <i class="mdi s-4 mdi-database"></i>
                            <span>ORFanBase</span>
                        </a>
                    </li>
                </ul>
            </div>

        </aside>
        <div class="content-wrapper">
            <div class="content">

                <div id="project-dashboard" class="page-layout simple right-sidebar">

                    <div class="page-content-wrapper">
                        <!-- CONTENT -->
                        <div class="page-content">
                            <!-- ORFan Genes -->
                            <div class="col-12 col-sm-12 col-xl-12 p-3">
                                <div class="widget widget1 card">
                                    <div class="widget-header pl-4 pr-2">
                                        <div class="col">
                                            <h3 class="center">ORFanGenes</h3>
                                        </div>
                                    </div>

                                    <div class="widget-content pt-2 pb-8 d-flex flex-column align-items-center justify-content-center">
                                        <div class="sub-title h6 text-muted">
                                            <p>
                                                ORFanGenes is a web-based software engine that identifies ORFan genes from the genomes of specified species or from a given list of DNA sequences. The scope of the search for orphan genes can be defined by the selection of the taxonomy level of interest. Detectable homologous sequences are found for candidate gene in the NCBI databases. From these findings the ORFanID engine identifies and depicts orphan genes. Results may be viewed and analyzed graphically for the purpose of scientific research and inquiry.                                             </p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- / ORFan Genes -->

                            <!-- WIDGET GROUP -->
                            <div class="widget-group row no-gutters">
                                <section id="mystery-section">
                                    <div class="container">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <div class="section-details">
                                                    <p>
                                                        Orphan genes are an unraveling mystery. We hope that ORFanID will help reveal the intricacies of their origin and function.
                                                    </p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </section>

                                <!-- ORFan Genes -->
                                <div class="col-12 col-sm-6 col-xl-4 p-3">
                                    <div class="widget widget1 card">
                                        <div class="widget-header pl-4 pr-2">
                                            <div class="col">
                                                <i class="mdi s-10 mdi-dna"></i>
                                                <h5 class="center">ORFan Genes</h5>
                                            </div>
                                        </div>

                                        <div class="widget-content pt-2 pb-8 d-flex flex-column align-items-center justify-content-center">
                                            <div class="sub-title h6 text-muted">
                                                <p>
                                                    Orphan genes (also known as taxonomically restricted genes) are genes that do not have related ancestral genes in other species or at the specified taxonomy level. At the molecular level, ORFan genes consist of DNA sequences that have no homology with sequences found in common DNA databases such as Genbank. While the prevailing dogma has defined genes in different species as a result of gene duplication or recombination, the presence of orphan gene ubiquity in various sequenced genomes is a mystery, perhaps even a significant problem to be solved.
                                                </p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!-- / ORFan Genes -->

                                <!-- Biology -->
                                <div class="col-12 col-sm-6 col-xl-4 p-3">
                                    <div class="widget widget1 card">
                                        <div class="widget-header pl-4 pr-2">
                                            <div class="col">
                                                <i class="mdi s-10 mdi-tree"></i>
                                                <h5 class="center">Biology</h5>
                                            </div>
                                        </div>

                                        <div class="widget-content pt-2 pb-8 d-flex flex-column align-items-center justify-content-center">
                                            <div class="sub-title h6 text-muted">
                                                <p>
                                                    Historically, gene function is known to be expressed through proteins. There are specific organisms that have been found with unique proteins expressed by orphan genes such as Hydra, various Mollusks, Salamander and others. It appears that the anatomy of Hydra is mediated by orphan genes that give rise to unique proteins. Similarly, the mantle of various Mollusks has been found to be expressed from orphan genes, while the regeration of salamander limbs are mediated by orphans (Dr. Paul Nelson, 2017)
                                                </p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!-- / Biology -->

                                <!-- Discovery -->
                                <div class="col-12 col-sm-6 col-xl-4 p-3">
                                    <div class="widget widget1 card">
                                        <div class="widget-header pl-4 pr-2">
                                            <div class="col">
                                                <i class="mdi s-10 mdi-magnify"></i>
                                                <h5 class="center">Discovery</h5>
                                            </div>
                                        </div>

                                        <div class="widget-content pt-2 pb-8 d-flex flex-column align-items-center justify-content-center">
                                            <div class="sub-title h6 text-muted">
                                                <p>
                                                    By identifying these unique DNA sequences, ORFanID can help discover the origin, function and other significance of orphan genes. The software is able to identify genes unique to genus, family, or species etc. at differing taxonomy levels. Based on the parameters specified, some of orphans (also called Taxonomy Restricted Genes) may or may not fall under the given classification for strict ORFans.   As such, ORFanID can help delineate the actual sequence and function of de novo genes discovered in species and at all levels of the taxonomy tree.
                                                </p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!-- / Discovery -->

                                <section id="frontier-section">
                                    <div class="container">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <div class="section-details">
                                                    <p>
                                                        Orphan genes, a frontier for discovery
                                                    </p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </section>

                                <!-- Get Started -->
                                <div class="col-12 col-sm-6 col-xl-3 p-3">
                                    <a href="/input" class="action">
                                        <div id="started" class="widget widget1 card action">
                                            <div class="widget-header pl-4 pr-2">
                                                <div class="col">
                                                    <i class="mdi s-30 mdi-cursor-pointer"></i>
                                                    <h3 class="center">Get Started</h3>
                                                </div>
                                            </div>
                                        </div>
                                    </a>
                                </div>
                                <!-- / Get Started -->

                                <!-- Instructions -->
                                <div class="col-12 col-sm-6 col-xl-3 p-3">
                                    <a href="https://docs.google.com/document/d/1VAhvAmmU4mQh-D93MVw4TfsgJZlo3VqIto1sOw8oOt4/edit?usp=sharing" class="action">
                                        <div id="instructions" class="widget widget1 card">
                                            <div class="widget-header pl-4 pr-2">
                                                <div class="col">
                                                    <i class="mdi s-30 mdi-library-books"></i>
                                                    <h3 class="center">Instructions</h3>
                                                </div>
                                            </div>
                                        </div>
                                    </a>
                                </div>
                                <!-- / Instructions -->

                                <!-- Results -->
                                <div class="col-12 col-sm-6 col-xl-3 p-3">
                                    <a href="" class="action">
                                        <div id="results" class="widget widget1 card action">
                                            <div class="widget-header pl-4 pr-2">
                                                <div class="col">
                                                    <i class="mdi s-30 mdi-clipboard"></i>
                                                    <h3 class="center">Results</h3>
                                                </div>
                                            </div>
                                        </div>
                                    </a>
                                </div>
                                <!-- / Results -->

                                <!-- ORFanBase -->
                                <div class="col-12 col-sm-6 col-xl-3 p-3">
                                    <a href="" class="action">
                                        <div id="orfanbase" class="widget widget1 card action">
                                            <div class="widget-header pl-4 pr-2">
                                                <div class="col">
                                                    <i class="mdi s-30 mdi-database"></i>
                                                    <h3 class="center">ORFanBase</h3>
                                                </div>
                                            </div>
                                        </div>
                                    </a>
                                </div>
                                <!-- / ORFanBase -->

                                <!-- References -->
                                <div class="col-12 col-sm-12 col-xl-12 p-12">
                                    <div class="widget widget1 card">
                                        <div class="widget-header pl-4 pr-2">
                                            <div class="col">
                                                <h5 class="center">References</h5>
                                            </div>
                                        </div>

                                        <div class="widget-content pt-2 pb-8 d-flex flex-column align-items-center justify-content-center">
                                            <div class="sub-title h6 text-muted">
                                                <p>Altschul, S.F., Gish, W., Miller, W., Myers, E.W. & Lipman, D.J. (1990) "Basic local alignment search tool." J. Mol. Biol. 215:403-410</p>
                                                <p>Clamp, M., Fry, B., Kamal, M., Xie, X., Cuff, J., Lin, M.F., Kellis, K., Lindblad-Toh, K., and Lander, E. S. (2007) “Distinguishing protein-coding and noncoding genes in the human genome”. PNAS 2007 December, 104 (49) 19428-19433</p>
                                                <p>Ekstrom, A. & Yin, Y. (2016) "ORFanFinder: automated identification of taxonomically restricted orphan genes." Bioinformatics; 32 (13): 2053-2055. doi: 10.1093/bioinformatics/btw122</p>
                                                <p>Gunasekera, R. S., Hewapathirana, S., Gunasekera, V., and Nelson P., (2018),
                                                    A Web-Based Computational Algorithm, ORFanID, for Discovering and Cataloging Orphan and Taxonomically Restricted Genes in Various Species, Proceedings of the International Society for Computational Biology, 26th Conference on Intelligent Systems in Molecular Biology, Chicago, IL, USA</p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!-- / References -->
                            </div>


                            <div class="footer-copyright">
                                <div class="container"><a class="brown-text text-lighten-3 center-align" href="#"></a>
                                    <p class="center-align">Copyright &nbsp;© 2017-2018</p>
                                </div>
                            </div>
                        </div>
                        <!-- / CONTENT -->
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>
</body>
