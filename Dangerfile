# Make it more obvious that a PR is a work in progress and shouldn't be merged yet
warn("PR is classed as Work in Progress") if github.pr_title.include? "[WIP]"

# Warn when there is a big PR
warn("Big PR") if git.lines_of_code > 500

# General
failure "Please provide a summary in the Pull Request description" if github.pr_body.length < 5
warn "This PR does not have any assignees yet." unless github.pr_json["assignee"]
can_merge = github.pr_json["mergeable"]
warn("This PR cannot be merged yet.", sticky: false) unless can_merge
github.dismiss_out_of_range_messages

# General
github.dismiss_out_of_range_messages

# CheckstyleFormat
checkstyle_format.base_path = Dir.pwd
checkstyle_format.report 'build/reports/detekt/detekt.xml' 	