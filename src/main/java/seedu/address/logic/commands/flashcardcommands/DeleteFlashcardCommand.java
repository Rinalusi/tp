package seedu.address.logic.commands.flashcardcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FLASHCARD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FLASHCARDSET;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.FlashcardModel;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.flashcard.FlashcardSet;

/**
 * Regulates the behaviour of a {@code Command} that deletes a {@code Flashcard}
 * from a {@code FlashcardSet}
 */
public class DeleteFlashcardCommand extends Command<FlashcardModel> {

    public static final String COMMAND_WORD = "delete fl";
    public static final String MESSAGE_DELETE_FLASHCARD_SUCCESS = "Deleted flashcard: %1$s";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes a single flashcard in a specified flashcard set. "
            + "identified by the index number used in the displayed flashcard set list.\n"
            + "Parameters: "
            + PREFIX_FLASHCARDSET + "<index (positive integer)>"
            + PREFIX_FLASHCARD + "<index (positive integer)>\n"
            + "Example: "
            + COMMAND_WORD + " "
            + PREFIX_FLASHCARDSET + "1 "
            + PREFIX_FLASHCARD + "3";

    private final Index targetFlashcardSetIndex;
    private final Index targetFlashcardIndex;

    /**
     * Creates a {@code Command} to delete a specified {@code Flashcard}
     * from a specified {@code FlashcardSet}
     */
    public DeleteFlashcardCommand(Index targetFlashcardSetIndex, Index targetFlashcardIndex) {
        this.targetFlashcardSetIndex = targetFlashcardSetIndex;
        this.targetFlashcardIndex = targetFlashcardIndex;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteFlashcardCommand // instanceof handles nulls
                && targetFlashcardIndex.equals(((DeleteFlashcardCommand) other).targetFlashcardIndex) // state check
                && targetFlashcardSetIndex.equals(((DeleteFlashcardCommand) other).targetFlashcardSetIndex));
    }

    @Override
    public CommandResult execute(FlashcardModel model) throws CommandException {
        requireNonNull(model);
        List<FlashcardSet> lastShownFlashcardSetList = model.getFilteredFlashcardSetList();

        if (targetFlashcardSetIndex.getZeroBased() >= lastShownFlashcardSetList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_FLASHCARDSET_DISPLAYED_INDEX);
        }

        FlashcardSet flashcardSet = model.getFlashcardSet(targetFlashcardSetIndex);

        if (targetFlashcardIndex.getZeroBased() >= flashcardSet.getSize()) {
            throw new CommandException(Messages.MESSAGE_INVALID_FLASHCARD_INDEX);
        }

        Flashcard flashcardToDelete = model.getFlashcard(flashcardSet, targetFlashcardIndex);
        model.deleteFlashcard(flashcardSet, targetFlashcardIndex);
        return new CommandResult(String.format(MESSAGE_DELETE_FLASHCARD_SUCCESS, flashcardToDelete));
    }
}
