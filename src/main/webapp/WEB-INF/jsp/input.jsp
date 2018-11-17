<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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
                <div class="col offset-s2 s10">
                    <div class="row">
                        <div class="col s10">
                            <div class="input-field">
                                <input type="text" id="ncbi_accession_input" class="input-field" name="accession">
                                <label for="ncbi_accession_input">NCBI Accession</label>
                            </div>
                            <div class="col s2">
                                <a class="waves-effect waves-light btn" type="button" id="findsequence" name="findsequence"><i class="large material-icons">search</i></a>
                            </div>
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
                            <input id="fastafile" type="file" accept=".fasta" onchange="setFileContnet(this.value);">
                            <i class="large material-icons">cloud_upload</i>
                        </div>
                    </div>
            </div>
        </div>
        <div class="row">
            <div class="col offset-s1 s10">
                <div class="input-field">
                    <textarea id="genesequence" hight="100px;overflow-y: auto;" name="sequence" class="materialize-textarea" raw="3">
                        </textarea>
                    <label for="genesequence">Gene Sequence</label>
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
        </div>
        <div class="row hidden" id="advanceparameterssection">
            <div class="col offset-s1 s10">
                <h6>Advanced parameters:</h6><br>
                <p class="range-field">
                    <label for="maxevalue">Maximum E-value for BLAST(e-10):</label>
                    <input type="range" id="maxevalue"  name="maxEvalue" min="1" max="10" value="3"/>
                    <label for="maxtargets">Maximum target sequences for BLAST:</label>
                    <input type="range" id="maxtargets" name="maxTargetSequence" min="100" max="1000" value="{{Config::get('orfanid.default_maxtargetseq')}}"/>
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
        <div id="input_progressbar" class="modal" >
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