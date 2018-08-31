<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%--
  Created by IntelliJ IDEA.
  User: savidude
  Date: 8/29/18
  Time: 9:36 PM
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
    <!-- Style CSS -->
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
    <!-- Input JS -->
    <script type="text/javascript" src="assets/js/input.js"></script>
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
            <div class="content">

                <div id="project-dashboard" class="page-layout simple right-sidebar">

                    <div class="page-content-wrapper">
                        <!-- CONTENT -->
                        <div class="page-content">
                            <div class="container">
                                <form:form method="post" modelAttribute="sequence" action="/store" >
                                    <fieldset class="form-group">
                                        <div class="row">
                                            <label class="col-form-legend col-sm-2">BLAST Type</label>
                                            <div class="col-sm-10">
                                                <div class="form-check">
                                                    <label class="form-check-label">
                                                        <form:radiobutton path="type" class="form-check-input" id="protein" name="type" value="protein"></form:radiobutton>
                                                        <span class="radio-icon"></span>
                                                        <span class="form-check-description">Protein</span>
                                                    </label>
                                                </div>
                                                <div class="form-check">
                                                    <label class="form-check-label">
                                                        <form:radiobutton path="type" class="form-check-input" id="nucleotide" name="type" value="nucleotide"></form:radiobutton>
                                                        <span class="radio-icon"></span>
                                                        <span class="form-check-description">Nucleotide</span>
                                                    </label>
                                                </div>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <div class="form-group row">
                                        <label for="fastafile" class="col-sm-2 col-form-label">Sequence</label>
                                        <div class="col-sm-10">
                                            <form:textarea path="genesequence" class="form-control" id="genesequence" rows="3" name="genesequence"></form:textarea>
                                            <br>
                                            <div class="custom-file">
                                                <input type="file" class="custom-file-input" id="fastafile" accept=".fasta" onchange="setFileContent(this.value);">
                                                <label class="custom-file-label" for="fastafile" id="filename">Choose file</label>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label for="organismName" class="col-sm-2 col-form-label">Organism</label>
                                        <div class="col-sm-10 autocomplete">
                                            <form:input path="organismName" type="text" class="form-control" id="organismName" name="organismName"></form:input>
                                        </div>
                                    </div>

                                    <div class="hidden" id="advanceparameterssection">
                                        <h6>Advanced parameters:</h6><br>
                                        <div class="form-group row">
                                            <label for="maxevalue" class="col-sm-2 col-form-label">Maximum E-value for BLAST(e-10)</label>
                                            <div class="col-sm-10" display=inline>
                                                <form:input path="maxevalue" type="range" id="maxevalue"  name="maxevalue" min="1" max="10" value="3"></form:input>
                                                <label id="maxevaluevalue" class="valuelabel">3</label>
                                            </div>
                                        </div>
                                        <div class="form-group row">
                                            <label for="maxtargets" class="col-sm-2 col-form-label">Maximum target sequences for BLAST</label>
                                            <div class="col-sm-10" display=inline>
                                                <form:input path="maxtargets" type="range" id="maxtargets" name="maxtargets" min="100" max="1000" value="1000"></form:input>
                                                <label id="maxtargetsvalue" class="valuelabel">1000</label>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="row">
                                        <div class="col offset-s1 s4">
                                            <a id="load-example-data">Example</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;
                                            <a id="advanceparameterslink">Advanced parameters</a>
                                        </div>
                                        <div class="col offset-s7 s2">
                                            <button class="btn btn-primary" type="submit" id="submit">Submit
                                                <i class="mdi mdi-send"></i>
                                            </button>
                                        </div>
                                    </div>

                                    <div id="modal1" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="exampleModalLiveLabel" aria-hidden="true">
                                        <div class="modal-dialog" role="document">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h5 class="modal-title">ORFanID In Progress....</h5>
                                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                        <span aria-hidden="true">×</span>
                                                    </button>
                                                </div>
                                                <div class="modal-body">
                                                    <div class="row">
                                                        <div class="col s12">
                                                            <div class="col offset-s2 s1">

                                                            </div>
                                                            <div  class="col s4">
                                                                <img src="assets/images/loading4.gif" alt="Loading">
                                                            </div >
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </form:form>
                            </div>
                        </div>
                        <!-- / CONTENT -->
                    </div>
                </div>
            </div>

            <footer class="footer-copyright">
                <div class="container"><a class="brown-text text-lighten-3 center-align" href="#"></a>
                    <p class="center-align">Copyright &nbsp;© 2017-2018</p>
                </div>
            </footer>
        </div>
    </div>
</main>
</body>
