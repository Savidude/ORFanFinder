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
    <link type="text/css" rel="stylesheet" href="assets/css/styles.css">
    <link type="text/css" rel="stylesheet" href="https://ebi.emblstatic.net/web_guidelines/EBI-Icon-fonts/v1.2/fonts.css">
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
            <li><a href="/results">Results</a></li>
            <li><a href="/instructions">Instructions</a></li>
        </ul>
    </div>
</nav>
<main>
    <div class="row">
        <div class="col 10 offset-s1">
            <h3>ORFanID Instructions</h3>
            <h4>How to Run an Example Sequence</h4>

            <p>1. Visit the ORFanGenes website (http://orfangenes.com/) and click <Strong>Get Started</Strong>.</p>
            <img class="doc" src="assets/images/documentation/GetStarted.png">

            <p>2. Click on the "Example" link on the bottom left. The text fields will automatically get filled with an example input sequence of genes of E. Coli.</p>
            <img class="doc" src="assets/images/documentation/example.png">

            <p>3. The E-Value defaults to e-3 and the Maximum Target Sequences for BLAST defaults to 1000. If these need to be changed, "Advanced Parameters" can be selected and move the slider to select the appropriate values.</p>
            <p>4. Select "Submit" and wait for around 10 minutes or more until ORFanGenes processes the input sequence and returns the results.</p>
            <img class="doc" src="assets/images/documentation/classify.png">

            <p>5. View the results of the example sequence</p>
            <img class="doc" src="assets/images/documentation/result.png">

            <p>The example input contains three genes. Out of the three genes, two of which are Genus Restricted Genes, and the other is an ORFan Gene. The Blast Results table shows the taxonomies (and their parents) for each gene in the input sequence from the BLAST search.</p>

            <h4>Using ORFanGenes</h4>
            <p>1. In the input page, upload a sequence file. The file must be in .fasta format, and must contain the geneid value of each gene.</p>
            <p>2. Begin typing the name of the input organism and select the name from the suggested results, and select the organism from the dropdown menu.</p>
            <img class="doc" src="assets/images/documentation/input.png">
            <p>3. Select “Advanced parameters” and adjust the maximum E-value and maximum target sequences for BLAST. </p>
            <img class="doc" src="assets/images/documentation/advanced.png">
            <p>4. Click “Submit” and wait for results to load. The time taken for the results to be obtained is dependent on the number of input sequences, selected e-value, and number of target sequences.</p>

            <h4>ORFanGenes Algorithm</h4>
            <p>Let us imagine a case where sequences (genes) from a human are provided as the input sequence. Therefore, the input taxonomy should be that of Homo sapiens. The first step is to get the lineage of Homo sapiens from the species level up to the Domain (Superkingdom) level, as shown below.</p>
            <img class="doc" src="assets/images/documentation/algorithm/Step1.png">
            <p>The next step is to select a gene (sequence) from the set of input sequences, run it through BLAST, and obtain the homologous taxonomies. In this example, imagine the BLAST search for the gene obtained homologous taxonomies for 5 other species; owl, turtle, cat, dog, and chimpanzee (note that the taxonomies obtained from BLAST does not always have to be at the species level).</p>
            <img class="doc" src="assets/images/documentation/algorithm/Step2.png">
            <p>Then, the lineages of all the taxonomies are obtained.</p>
            <img class="doc" src="assets/images/documentation/algorithm/Step3.png">
            <p>The number of unique taxonomies at the Domain level are then obtained.</p>
            <img class="doc" src="assets/images/documentation/algorithm/Step4.png">
            <p>Since the number of unique taxonomies at the Domain level is 1 being Eukaryote, the next level is observed.</p>
            <img class="doc" src="assets/images/documentation/algorithm/Step5.png">
            <p>Since the number of unique taxonomies at the Kingdom level is 1 being Animalia, the Phylum level is observed.</p>
            <img class="doc" src="assets/images/documentation/algorithm/Step6.png">
            <p>Similarly, the number of unique taxonomies at the Phylum level is observed to be 1 being Chordata</p>
            <p>It can be seen that there are multiple taxonomies at the Class level. As a result, the processed gene is classified as a <b>Class Restricted Gene</b>.</p>
            <img class="doc" src="assets/images/documentation/algorithm/Step7.png">
        </div>
    </div>
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