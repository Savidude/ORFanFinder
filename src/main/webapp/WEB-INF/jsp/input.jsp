<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%--<body class="layout layout-vertical layout-left-navigation layout-above-toolbar layout-above-footer">--%>
<%--<main>--%>
    <%--<div id="wrapper">--%>
        <%--<div class="content-wrapper">--%>
            <%--<div class="content">--%>
                <%--<div id="project-dashboard" class="page-layout simple right-sidebar">--%>
                    <%--<div class="page-content-wrapper" >--%>
                        <%--<!-- CONTENT -->--%>
                        <%--<div class="page-content" style="background-color: #CFD8DC">--%>
                            <%--<div class="container">--%>
                                <%--<form:form method="post" modelAttribute="sequence" action="/store" >--%>
                                    <%--<fieldset class="form-group">--%>
                                        <%--<div class="row">--%>
                                            <%--<label class="col-form-legend col-sm-2">BLAST Type</label>--%>
                                            <%--<div class="col-sm-10">--%>
                                                <%--<div class="form-check">--%>
                                                    <%--<label class="form-check-label">--%>
                                                        <%--<form:radiobutton path="type" class="form-check-input" id="protein" name="type" value="protein"></form:radiobutton>--%>
                                                        <%--<span class="radio-icon"></span>--%>
                                                        <%--<span class="form-check-description">Protein</span>--%>
                                                    <%--</label>--%>
                                                <%--</div>--%>
                                                <%--<div class="form-check">--%>
                                                    <%--<label class="form-check-label">--%>
                                                        <%--<form:radiobutton path="type" class="form-check-input" id="nucleotide" name="type" value="nucleotide"></form:radiobutton>--%>
                                                        <%--<span class="radio-icon"></span>--%>
                                                        <%--<span class="form-check-description">Nucleotide</span>--%>
                                                    <%--</label>--%>
                                                <%--</div>--%>
                                            <%--</div>--%>
                                        <%--</div>--%>
                                    <%--</fieldset>--%>
                                    <%--<div class="form-group row">--%>
                                        <%--<label for="fastafile" class="col-sm-2 col-form-label" style="color: black">Sequence</label>--%>
                                        <%--<div class="col-sm-10">--%>
                                            <%--<form:textarea path="genesequence" class="form-control" id="genesequence" rows="3" name="genesequence" style="border:solid 1px black;"></form:textarea>--%>
                                            <%--<br>--%>
                                            <%--<div class="custom-file">--%>
                                                <%--<input type="file" class="custom-file-input" id="fastafile" accept=".fasta" onchange="setFileContent(this.value);">--%>
                                                <%--<label class="custom-file-label" for="fastafile" id="filename" style="background-color: #CFD8DC">Choose file</label>--%>
                                            <%--</div>--%>
                                        <%--</div>--%>
                                    <%--</div>--%>
                                    <%--<div class="form-group row">--%>
                                        <%--<label for="organismName" class="col-sm-2 col-form-label" style="color: black">Organism</label>--%>
                                        <%--<div class="col-sm-10 autocomplete">--%>
                                            <%--<form:input path="organismName" type="text" class="form-control" id="organismName" name="organismName"></form:input>--%>
                                        <%--</div>--%>
                                    <%--</div>--%>

                                    <%--<div class="hidden" id="advanceparameterssection">--%>
                                        <%--<h6>Advanced parameters:</h6><br>--%>
                                        <%--<div class="form-group row">--%>
                                            <%--<label for="maxevalue" class="col-sm-2 col-form-label">Maximum E-value for BLAST(e-10)</label>--%>
                                            <%--<div class="col-sm-10" display=inline>--%>
                                                <%--<form:input path="maxevalue" type="range" id="maxevalue"  name="maxevalue" min="1" max="10" value="3"></form:input>--%>
                                                <%--<label id="maxevaluevalue" class="valuelabel">3</label>--%>
                                            <%--</div>--%>
                                        <%--</div>--%>
                                        <%--<div class="form-group row">--%>
                                            <%--<label for="maxtargets" class="col-sm-2 col-form-label">Maximum target sequences for BLAST</label>--%>
                                            <%--<div class="col-sm-10" display=inline>--%>
                                                <%--<form:input path="maxtargets" type="range" id="maxtargets" name="maxtargets" min="100" max="1000" value="1000"></form:input>--%>
                                                <%--<label id="maxtargetsvalue" class="valuelabel">1000</label>--%>
                                            <%--</div>--%>
                                        <%--</div>--%>
                                    <%--</div>--%>

                                    <%--<div class="row">--%>
                                        <%--<div class="col offset-s1 s4">--%>
                                            <%--<a id="load-example-data">Example</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;--%>
                                            <%--<a id="advanceparameterslink">Advanced parameters</a>--%>
                                        <%--</div>--%>
                                        <%--<div class="col offset-s7 s2">--%>
                                            <%--<button class="btn btn-primary" type="submit" id="submit">Submit--%>
                                                <%--<i class="mdi mdi-send"></i>--%>
                                            <%--</button>--%>
                                        <%--</div>--%>
                                    <%--</div>--%>

                                    <%--<br><br><br><br>--%>

                                    <%--<div class="row">--%>
                                        <%--<!-- Get Started -->--%>
                                        <%--<div class="col-12 col-sm-6 col-xl-3 p-3">--%>
                                            <%--<a href="/input" class="action">--%>
                                                <%--<div id="started" class="widget widget1 card action" style="background-color: #455A64">--%>
                                                    <%--<div class="widget-header pl-4 pr-2">--%>
                                                        <%--<div class="col">--%>
                                                            <%--<i class="mdi s-8 mdi-cursor-pointer" style="color: #FFECB3"></i>--%>
                                                            <%--<h4 class="center" style="color: #ffe57f">Get Started</h4>--%>
                                                        <%--</div>--%>
                                                    <%--</div>--%>
                                                <%--</div>--%>
                                            <%--</a>--%>
                                        <%--</div>--%>
                                        <%--<!-- / Get Started -->--%>

                                        <%--<!-- Instructions -->--%>
                                        <%--<div class="col-12 col-sm-6 col-xl-3 p-3">--%>
                                            <%--<a href="https://docs.google.com/document/d/1VAhvAmmU4mQh-D93MVw4TfsgJZlo3VqIto1sOw8oOt4/edit?usp=sharing" class="action">--%>
                                                <%--<div id="instructions" class="widget widget1 card" style="background-color: #455A64">--%>
                                                    <%--<div class="widget-header pl-4 pr-2">--%>
                                                        <%--<div class="col">--%>
                                                            <%--<i class="mdi s-8 mdi-library-books" style="color: #FFECB3"></i>--%>
                                                            <%--<h4 class="center" style="color: #ffe57f">Instructions</h4>--%>
                                                        <%--</div>--%>
                                                    <%--</div>--%>
                                                <%--</div>--%>
                                            <%--</a>--%>
                                        <%--</div>--%>
                                        <%--<!-- / Instructions -->--%>

                                        <%--<!-- Results -->--%>
                                        <%--<div class="col-12 col-sm-6 col-xl-3 p-3">--%>
                                            <%--<a href="" class="action">--%>
                                                <%--<div id="results" class="widget widget1 card action" style="background-color: #455A64">--%>
                                                    <%--<div class="widget-header pl-4 pr-2">--%>
                                                        <%--<div class="col">--%>
                                                            <%--<i class="mdi s-8 mdi-clipboard" style="color: #FFECB3"></i>--%>
                                                            <%--<h4 class="center" style="color: #ffe57f">Results</h4>--%>
                                                        <%--</div>--%>
                                                    <%--</div>--%>
                                                <%--</div>--%>
                                            <%--</a>--%>
                                        <%--</div>--%>
                                        <%--<!-- / Results -->--%>

                                        <%--<!-- ORFanBase -->--%>
                                        <%--<div class="col-12 col-sm-6 col-xl-3 p-3">--%>
                                            <%--<a href="" class="action">--%>
                                                <%--<div id="orfanbase" class="widget widget1 card action" style="background-color: #455A64">--%>
                                                    <%--<div class="widget-header pl-4 pr-2">--%>
                                                        <%--<div class="col">--%>
                                                            <%--<i class="mdi s-8 mdi-database" style="color: #FFECB3"></i>--%>
                                                            <%--<h4 class="center" style="color: #ffe57f">ORFanBase</h4>--%>
                                                        <%--</div>--%>
                                                    <%--</div>--%>
                                                <%--</div>--%>
                                            <%--</a>--%>
                                        <%--</div>--%>
                                        <%--<!-- / ORFanBase -->--%>
                                    <%--</div>--%>

                                    <%--<div id="modal1" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="exampleModalLiveLabel" aria-hidden="true">--%>
                                        <%--<div class="modal-dialog" role="document">--%>
                                            <%--<div class="modal-content">--%>
                                                <%--<div class="modal-header">--%>
                                                    <%--<h5 class="modal-title">ORFanID In Progress....</h5>--%>
                                                    <%--<button type="button" class="close" data-dismiss="modal" aria-label="Close">--%>
                                                        <%--<span aria-hidden="true">×</span>--%>
                                                    <%--</button>--%>
                                                <%--</div>--%>
                                                <%--<div class="modal-body">--%>
                                                    <%--<div class="row">--%>
                                                        <%--<div class="col s12">--%>
                                                            <%--<div class="col offset-s2 s1">--%>

                                                            <%--</div>--%>
                                                            <%--<div  class="col s4">--%>
                                                                <%--<img src="assets/images/loading4.gif" alt="Loading">--%>
                                                            <%--</div >--%>
                                                        <%--</div>--%>
                                                    <%--</div>--%>
                                                <%--</div>--%>
                                            <%--</div>--%>
                                        <%--</div>--%>
                                    <%--</div>--%>
                                <%--</form:form>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                        <%--<!-- / CONTENT -->--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</div>--%>

            <%--<footer class="footer-copyright" style="background-color: #CFD8DC">--%>
                <%--<div class="container"><a class="brown-text text-lighten-3 center-align" href="#"></a>--%>
                    <%--<p class="center-align">Copyright &nbsp;© 2017-2018</p>--%>
                <%--</div>--%>
            <%--</footer>--%>
        <%--</div>--%>
    <%--</div>--%>
<%--</main>--%>
<%--</body>--%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>ORFanGenes</title>
    <link rel="shortcut icon" type="image/x-icon" href="assets/favicon.ico" />
    <!--Import Google Icon Font-->
    <link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <!--Import materialize.css-->
    <link type="text/css" rel="stylesheet" href="assets/css/materialize.min.css" media="screen,projection" />
    <link type="text/css" rel="stylesheet" href="assets/css/orfanid-input.css">
    <link type="text/css" rel="stylesheet" href="assets/css/orfan_styles.css">
    <!--Let browser know website is optimized for mobile-->
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="csrf-token" content="{{ csrf_token() }}">
    <!--Import jQuery before materialize.js-->
    <script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="assets/js/materialize.min.js"></script>
    <script type="text/javascript" src="assets/js/plotly-latest.min.js"></script>
    <script type="text/javascript" src="assets/js/orfanid-input.js"></script>
</head>
<body>
<nav>
    <div class="nav-wrapper teal">
        <a href="/" class="brand-logo">ORFanID</a>
        <ul id="nav-mobile" class="right hide-on-med-and-down">
            <li><a href="/input">Home</a></li>
            <li><a href="/clamp">Clamp</a></li>
            <li><a href="/results">Results</a></li>
            <li><a href="/orfanbase">ORFanBase</a></li>
            <li><a href="https://docs.google.com/document/d/1VAhvAmmU4mQh-D93MVw4TfsgJZlo3VqIto1sOw8oOt4/edit?usp=sharing">Instructions</a></li>
        </ul>
    </div>
</nav>
<main>

    <form:form method="post" modelAttribute="sequence" action="/store" >
        <div class="row">
            <div class="col s6">
                <div class="row">
                    <div class="col offset-s2 s10">
                        <div class="input-field">
                            <input type="text" id="autocomplete-input" class="input-field" name="accession">
                            <label for="autocomplete-input">NCBI Accession</label>
                        </div>
                        <div>
                            <a class="waves-effect waves-light btn" type="button" id="findsequence" name="findsequence"><i class="large material-icons">search</i></a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col s6">
                    <div class="file-field input-field">
                        <div class="col s9 file-path-wrapper">
                            <input class="file-path validate"  id="fastaFileName" type="text" placeholder="Upload file">
                        </div>
                        <div class="btn">
                            <input id="fastafile"  type="file" accept=".fasta" onchange="setFileContnet(this.value);">
                            <i class="large material-icons">cloud_upload</i>
                        </div>
                    </div>
            </div>
        </div>
        <div class="row">
            <div class="col offset-s1 s10">
                <div class="input-field">
                    <textarea id="genesequence" hight="100px;overflow-y: auto;" name="genesequence" class="materialize-textarea" raw="3">
                        </textarea>
                    <label for="genesequence">Protein Sequence</label>
                </div>
            </div>
        </div>

    <div class="row">
        <div class="col s6">
            <div class="row">
                <div class="input-field col offset-s2 s10">
                    <input type="text" id="organismName" name="organismName" class="autocomplete">
                    <label for="organismName">Organism</label>
                </div>
            </div>
        </div>
        <div class="col s6">
            <div class="row">
                <div class="input-field col s10">
                    <select id="taxonomyLevels" name="taxonomyLevels">
                        <option value="" disabled selected>Choose your option</option>
                    </select>
                    <label>Taxonomy Level</label>
                </div>
            </div>
        </div>
    </div>
    <div class="row hidden" id="advanceparameterssection">
        <div class="col offset-s1 s10">
            <h6>Advanced parameters:</h6><br>
            <p class="range-field">
                <label for="maxevalue">Maximum E-value for BLAST(e-10):</label>
                <input type="range" id="maxevalue"  name="maxevalue" min="1" max="10" value="3"/>
                <label for="maxtargets">Maximum target sequences for BLAST:</label>
                <input type="range" id="maxtargets" name="maxtargets" min="100" max="1000" value="{{Config::get('orfanid.default_maxtargetseq')}}"/>
            </p>
        </div>
    </div>
    <div class="row">
        <div class="col offset-s1 s4">
            <a id="load-example-data" class="waves-effect waves-light">Example</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;
            <a id="advanceparameterslink" class="waves-effect waves-light">Advanced parameters</a>
        </div>
        <div class="col offset-s7 s2">
            <button class="btn waves-effect waves-light" type="submit" name="action" id="submit">Submit
                <i class="material-icons right">send</i>
            </button>
        </div>
    </div>
    <div id="modal1" class="modal" >
        <div class="modal-content">
            <h6>  ORFanID In Progress.... </h6>
            <div class="progress">
                <div class="indeterminate"></div>
            </div>
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
        <div class="modal-footer">
            <!-- <a href="#!" class="modal-action modal-close waves-effect waves-green btn-flat">Possible Close Implematioation</a> -->
        </div>
    </div>
    </form:form>
</main>
<footer class="page-footer teal lighten-1">
    <div class="container">
        <div class="row">
            <div class="col s12">
                <h5 class="white-text">ORFanID</h5>
                <span valign-wrapper>
        <p class="grey-text text-lighten-4">ORFanID is an open-source web application
          that is capable of predicting orphan genes from a given list of gene sequences of a specified species.
          Each candidate gene is searched against the BLAST non-redundant database
          to find any detectable homologous sequences using BLASP programme.
          </p>
      </span>
            </div>
        </div>
    </div>
    <div class="footer-copyright">
        <div class="container">
            © Copyright 2017-2018
            <a class="grey-text text-lighten-4 right" href="mailto:sureshhewabi@gmail.com?cc=vinodh@thegunasekeras.org,richgun01@gmail.com&Subject=ORFanID%20Online%20Issues">Contact Us</a>
        </div>
    </div>
</footer>
</body>
</html>