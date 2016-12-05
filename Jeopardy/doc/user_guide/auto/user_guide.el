(TeX-add-style-hook
 "user_guide"
 (lambda ()
   (TeX-run-style-hooks
    "latex2e"
    "article"
    "art10")
   (LaTeX-add-labels
    "sec:getting-started"
    "sec:brief-instructions"
    "sec:features"
    "sec:limitations"
    "sec:bugs"))
 :latex)

