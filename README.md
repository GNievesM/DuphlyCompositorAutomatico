# DuphlyCompositorAutomatico 

This thesis primarily consists of the development of a tool capable of automatically or assistedly composing music, categorizing the resulting system within the field of algorithmic composition and expert system. The system provides an environment that allows the user to create rules, which are then applied to automatically generate a melodic line. The proposed working environment was created in collaboration with a professional musician and composer.

The thesis includes a literature review conducted to assess the state of the art, a complete implementation of a grammar for creating twelve-bar blues harmonic bases, and the proposal and implementation of the aforementioned environment for generating rules for melodic line generation. The creation of these rules is divided into three main stages: two stages for selecting parts of the composition and one for modification. During the modification stage, a modification condition and an optimization or guarantee condition are used. Since there are generally multiple valid modifications and the input size is variable, the use of backtracking-type algorithms is discarded in favor of evolutionary algorithms, as they allow for more efficient solution search and the existence of diversity in the results.

Although the system is specifically applied to the composition of twelve-bar blues, this is not presented as a limitation but rather as a particular case of the system's capabilities.

In addition to real-time playback, the system provides output in PDF format, representing the result in a standard musical score, as well as output in MIDI file format.
