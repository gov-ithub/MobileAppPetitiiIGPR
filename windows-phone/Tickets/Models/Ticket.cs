using Caliburn.Micro;
using System;
using System.Collections.Generic;

namespace Tickets.Models
{
    public class Ticket : PropertyChangedBase
    {
        private string _id;
        private TicketType _type;
        private DateTimeOffset _creationDate;
        private DateTimeOffset? _answerDate;
        private TicketAnswerType _answerType;
        private bool? _isAnswered;

        public string Id
        {
            get { return _id; }
            set { _id = value; NotifyOfPropertyChange(); }
        }

        public TicketType Type
        {
            get { return _type; }
            set { _type = value; NotifyOfPropertyChange(); }
        }

        public DateTimeOffset CreationDate
        {
            get { return _creationDate; }
            set { _creationDate = value; NotifyOfPropertyChange(); }
        }

        public bool? IsAnswered
        {
            get { return _isAnswered; }
            set { _isAnswered = value; NotifyOfPropertyChange(); }
        }

        public DateTimeOffset? AnswerDate
        {
            get { return _answerDate; }
            set { _answerDate = value; NotifyOfPropertyChange(); }
        }

        public TicketAnswerType AnswerType
        {
            get { return _answerType; }
            set { _answerType = value; NotifyOfPropertyChange(); }
        }

        public static readonly List<string> Counties = new List<string>
        {
            "Alba",
            "Arad",
            "Argeș",
            "Bacău",
            "Bihor",
            "Bistrița-Năsăud",
            "Botoșani",
            "Brăila",
            "Brașov",
            "București",
            "Buzău",
            "Călărași",
            "Caraș-Severin",
            "Cluj",
            "Constanța",
            "Covasna",
            "Dîmbovița",
            "Dolj",
            "Galați",
            "Giurgiu",
            "Gorj",
            "Harghita",
            "Hunedoara",
            "Ialomița",
            "Iași",
            "Ilfov",
            "Maramureș",
            "Mehedinți",
            "Mureș",
            "Neamț",
            "Olt",
            "Prahova",
            "Sălaj",
            "Satu Mare",
            "Sibiu",
            "Suceava",
            "Teleorman",
            "Timiș",
            "Tulcea",
            "Vaslui",
            "Vîlcea",
            "Vrancea"
        };
    }
}
