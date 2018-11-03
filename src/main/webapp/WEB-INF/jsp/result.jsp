<%--
  Created by IntelliJ IDEA.
  User: savidude
  Date: 8/31/18
  Time: 11:19 AM
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
    <!-- DataTables -->
    <link type="text/css" rel="stylesheet" href="assets/bower_components/datatables.net-dt/css/jquery.dataTables.css" />
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
    <!-- DataTables -->
    <script type="text/javascript" src="assets/bower_components/datatables.net/js/jquery.dataTables.min.js"></script>
    <!-- plot.ly -->
    <script type="text/javascript" src="assets/bower_components/plotlyjs/plotly.js"></script>
     <%--Echarts --%>
    <script type="text/javascript" src="assets/bower_components/echarts/dist/echarts.min.js"></script>
    <!-- Main JS -->
    <script type="text/javascript" src="assets/js/main.js"></script>
    <!-- Result JS -->
    <script type="text/javascript" src="assets/js/result.js"></script>
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
                        <a class="nav-link ripple" href="/" data-url="/">
                            <i class="mdi s-4 mdi-home"></i>
                            <span>Home</span>
                        </a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link ripple active" href="/input" data-url="/">
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
            <%--<div class="content">--%>

                <div id="project-dashboard" class="page-layout simple right-sidebar">

                    <div class="page-content-wrapper">
                        <!-- CONTENT -->
                        <div class="page-content">
                            <div class="result-container">
                                <div>
                                    <button class="btn btn-primary" id="saveresult" data-toggle="modal" data-target="#saveResultModal">Save Result
                                        <i class="mdi mdi-send"></i>
                                    </button>
                                    <br><br>
                                </div>
                                <div class="row">
                                    <div class="col s5 offset-s1 center-align">
                                        <p class="h4"> ORFan Genes</p>
                                        <table id="orfanGenesSummary" class="table display">
                                            <thead>
                                            <tr>
                                                <th class="secondary-text">
                                                    <div class="table-header">
                                                        <span class="column-title">Taxonomy Level</span>
                                                    </div>
                                                </th>
                                                <th class="secondary-text">
                                                    <div class="table-header">
                                                        <span class="column-title">No of orphan genes</span>
                                                    </div>
                                                </th>
                                            </tr>
                                            </thead>
                                        </table>
                                    </div>
                                    <div class="col s6 center-align">
                                        <p class="h4"> ORFan gene summary</p>
                                        <div id="genesummary">
                                        </div>
                                    </div>
                                </div>

                                <br><hr>

                                <div class="section">
                                    <div id="ORFanGenesTable" class="row">
                                        <div class="col s10 offset-s1 center-align">
                                            <p class="h3"> ORFan Genes</p>
                                            <table id="orfanGenes" class="table display">
                                                <thead>
                                                <tr>
                                                    <th class="secondary-text">
                                                        <div class="table-header">
                                                            <span class="column-title">Gene</span>
                                                        </div>
                                                    </th>
                                                    <th class="secondary-text">
                                                        <div class="table-header">
                                                            <span class="column-title">Description</span>
                                                        </div>
                                                    </th>
                                                    <th class="secondary-text">
                                                        <div class="table-header">
                                                            <span class="column-title">ORFan Gene Level</span>
                                                        </div>
                                                    </th>
                                                    <th class="secondary-text">
                                                        <div class="table-header">
                                                            <span class="column-title">Taxonomy Level</span>
                                                        </div>
                                                    </th>
                                                </tr>
                                                </thead>
                                            </table>
                                        </div>
                                    </div>
                                </div>

                                <br><hr>

                                <div class="section">
                                    <div id="BlastResultsTable" class="row">
                                        <div class="col s10 offset-s1 center-align">
                                            <p class="h3">BLAST Results</p>
                                            <div id="blastResults"></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- / CONTENT -->
                    </div>
                </div>
            <%--</div>--%>
            <div id="saveResultModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="exampleModalLiveLabel" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">Save Result</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">×</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <label for="email" class="col-sm-2 col-form-label">Email</label>
                            <div class="col-sm-10">
                                <input type="email" class="form-control" id="email" placeholder="Email">
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                            <button type="button" class="btn btn-primary">Save Result</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>
</body>
