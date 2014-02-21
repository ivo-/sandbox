# NOTES:
#
#  = run with watchr .watchr
#  = works on windows
#

# TODO: restart for new files

# --------------------------------------------------
# Setup
# --------------------------------------------------

TEMPLATE = './test/test_template.rb'

system("cls")
system("echo Watcher activated ...")

# --------------------------------------------------
# Utils
# --------------------------------------------------


def create_test_file(src)
  FileUtils.mkdir_p File.dirname(src)
  FileUtils.cp TEMPLATE, src

  system("echo Tests file is created ...")
end

# --------------------------------------------------
# Rules
# --------------------------------------------------

watch('^test/.*_test.rb') do |m|
  system("cls && echo running #{m[0]} ...")
  system("ruby -Itest #{m[0]}")
end

watch('^lib/(.*)\.rb'   ) do |m|
  tests_for_file = "test/#{m[1]}_test.rb"
  create_test_file(tests_for_file) unless File.exists? tests_for_file

  system("cls && echo running #{tests_for_file} ...")
  system("ruby -Itest #{tests_for_file}")
end
